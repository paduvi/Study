package tree;

/** Class Node **/
class SplayNode<T extends Comparable<T>>{    
	SplayNode<T> left, right, parent;
	T element;

	/** Constructor **/
	public SplayNode()	{
		this(null, null, null, null);
	}          
	/** Constructor **/
	public SplayNode(T ele)	{
		this(ele, null, null, null);
	} 
	/** Constructor **/
	public SplayNode(T ele, SplayNode<T> left, SplayNode<T> right, SplayNode<T> parent)	{
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.element = ele;         
	}    
}

/** Class SplayTree **/
public class SplayTree<T extends Comparable<T>>{
	public SplayNode<T> root;

	/** Constructor **/
	public SplayTree(){
		root = null;
	}

	/** Function to check if tree is empty **/
	public boolean isEmpty(){
		return root == null;
	}

	/** clear tree **/
	public void clear(){
		root = null;
	}

	/** get depth **/
	public int getDepth(){
		return getDepth(root);
	}

	private int getDepth(SplayNode<T> r){
		if (r == null) {
			return -1;
		}
		return 1 + Math.max(getDepth(r.left), getDepth(r.right));
	}

	/** function to insert element */
	public void insert(T ele){
		SplayNode<T> z = root;
		SplayNode<T> p = null;
		while (z != null){
			p = z;
			if (ele.compareTo(p.element) < 0)
				z = z.left;
			else if(ele.compareTo(p.element) > 0)
				z = z.right;
			else
				return;
		}
		z = new SplayNode<T>();
		z.element = ele;
		z.parent = p;
		if (p == null)
			root = z;
		else if (ele.compareTo(p.element) > 0)
			p.right = z;
		else if (ele.compareTo(p.element) < 0)
			p.left = z;
		splay(z);
	}

	/** Xoay phải (giống AVL) **/
	public void makeLeftChildParent(SplayNode<T> c, SplayNode<T> p){
		if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
			return;

		if (p.parent != null){
			if (p == p.parent.left)
				p.parent.left = c;
			else 
				p.parent.right = c;
		}
		if (c.right != null)
			c.right.parent = p;

		c.parent = p.parent;
		p.parent = c;
		p.left = c.right;
		c.right = p;
	}

	/** Xoay trái (giống AVL) **/
	public void makeRightChildParent(SplayNode<T> c, SplayNode<T> p) {
		if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
			return;
		if (p.parent != null){
			if (p == p.parent.left)
				p.parent.left = c;
			else
				p.parent.right = c;
		}
		if (c.left != null)
			c.left.parent = p;
		c.parent = p.parent;
		p.parent = c;
		p.right = c.left;
		c.left = p;
	}

	/** function splay **/
	private void splay(SplayNode<T> x){
		while (x.parent != null){
			SplayNode<T> Parent = x.parent;
			SplayNode<T> GrandParent = Parent.parent;
			if (GrandParent == null) {
				if (x == Parent.left)
					makeLeftChildParent(x, Parent); // zig left
				else
					makeRightChildParent(x, Parent);  // zig right                
			} else {
				if (x == Parent.left){
					if (Parent == GrandParent.left){ // zigzig left
						makeLeftChildParent(Parent, GrandParent);
						makeLeftChildParent(x, Parent);
					} else { // zigzag left
						makeLeftChildParent(x, x.parent);
						makeRightChildParent(x, x.parent);
					}
				} else {
					if (Parent == GrandParent.left){ // zigzag right
						makeRightChildParent(x, x.parent);
						makeLeftChildParent(x, x.parent);
					} else { // zigzig right
						makeRightChildParent(Parent, GrandParent);
						makeRightChildParent(x, Parent);
					}
				}
			}
		}
		root = x;
	}

	/** function to remove element **/
	public void remove(T ele)
	{
		SplayNode<T> node = findNode(ele);
		remove(node);
	}

	/** function to remove node **/
	private void remove(SplayNode<T> node)
	{
		if (node == null)
			return;

		splay(node);
		if( (node.left != null) && (node.right !=null))
		{ 
			SplayNode<T> min = node.left;
			while(min.right!=null)
				min = min.right;

			node.left.parent = null;
			splay(min);
			min.right = node.right;
			min.right.parent = min;
			root = min;
		}
		else if (node.right != null)
		{
			node.right.parent = null;
			root = node.right;
		} 
		else if( node.left !=null)
		{
			node.left.parent = null;
			root = node.left;
		}
		else
		{
			root = null;
		}
		node.parent = null;
		node.left = null;
		node.right = null;
		node = null;
	}

	/** Functions to search for an element **/
	public boolean search(T val)
	{
		return findNode(val) != null;
	}
	private SplayNode<T> findNode(T ele)
	{
		SplayNode<T> z = root;
		while (z != null)
		{
			if (ele.compareTo(z.element) < 0)
				z = z.left;
			else if (ele.compareTo(z.element) > 0)
				z = z.right;
			else
				return z;
		}
		return null;
	}

	/** Function for inorder traversal **/ 
	public void inorder()
	{
		inorder(root);
	}
	private void inorder(SplayNode<T> r)
	{
		if (r != null)
		{
			inorder(r.left);
			System.out.print(r.element +" ");
			inorder(r.right);
		}
	}

	/** Function for preorder traversal **/
	public void preorder()
	{
		preorder(root);
	}
	private void preorder(SplayNode<T> r)
	{
		if (r != null)
		{
			System.out.print(r.element +" ");
			preorder(r.left);             
			preorder(r.right);
		}
	}

	/** Function for postorder traversal **/
	public void postorder()
	{
		postorder(root);
	}
	private void postorder(SplayNode<T> r)
	{
		if (r != null)
		{
			postorder(r.left);             
			postorder(r.right);
			System.out.print(r.element +" ");
		}
	}     
}