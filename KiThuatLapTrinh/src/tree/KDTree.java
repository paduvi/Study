package tree;

import java.util.Stack;

class KDNode {
	KDNode left, right;
	int[] coordinate = new int[2];
	
	public KDNode(int x, int y){
		this.coordinate[0] = x;
		this.coordinate[1] = y;
	}
	
	@Override
	public String toString(){
		return "("+ this.coordinate[0] +"; " + this.coordinate[1] + ")";
	}
}

public class KDTree {
	KDNode root;
	final int D = 2; // 2 dimensions
	
	public KDTree() {
		root = null;
	}
	
	public void clear(){
		root = null;
	}
	
	/** check empty **/
	public boolean isEmpty() {
		return root == null;
	}
	
	/** get depth **/
	public int getDepth(){
		return getDepth(root);
	}
	
	private int getDepth(KDNode node){
		if(node == null)
			return -1;
		return 1 + Math.max(getDepth(node.left), getDepth(node.right));
	}
	
	/** search **/
	public boolean search(int x, int y){
		int[] coordinate = new int[]{x,y};
		return search(root, coordinate, 0);
	}
	
	private boolean search(KDNode node, int[] coordinate, int discrim){
		if(node == null)
			return false;
		if(node.coordinate[0] == coordinate[0] && node.coordinate[1] == coordinate[1])
			return true;
		if(coordinate[discrim] < node.coordinate[discrim])
			return search(node.left, coordinate, (discrim + 1) % D);
		else
			return search(node.right, coordinate, (discrim + 1) % D);
	}
	
	/** insert **/
	public void insert(int x, int y){
		int[] coordinate = new int[]{x,y};
		root = insert(coordinate, root, 0);
	}
	
	private KDNode insert(int[] coordinate, KDNode node, int discrim){
		if(node == null)
			return new KDNode(coordinate[0], coordinate[1]);
		if(coordinate[discrim] < node.coordinate[discrim])
			node.left = insert(coordinate, node.left, (discrim + 1) % D);
		else
			node.right = insert(coordinate, node.right, (discrim + 1) % D);
		return node;
	}
	
	/** remove **/
	public void remove(int x, int y){
		int[] coordinate = new int[]{x,y};
		remove(coordinate, root, 0);
	}
	
	private KDNode remove(int[] coordinate, KDNode node, int discrim){
		if(node == null)
			return null;
		else if(node.coordinate[0] == coordinate[0] && node.coordinate[1] == coordinate[1]){
			if(node.right != null){
				node.coordinate = findMin(node.right, discrim, (discrim + 1) % D);
				node.right = remove(node.coordinate, node.right, (discrim + 1) % D);
			}else if(node.left != null){
				node.coordinate = findMin(node.right, discrim, (discrim + 1) % D);
				node.right = remove(node.coordinate, node.left, (discrim + 1) % D);
				node.left = null;
			}else
				node = null;
		}else if(coordinate[discrim] < node.coordinate[discrim]){
			node.left = remove(coordinate, node.left, (discrim + 1) % D);
		}else
			node.right = remove(coordinate, node.right, (discrim + 1) % D);
		return node;
	}
	
	/** find min discriminator of tree **/
	private int[] findMin(KDNode node, int discrim, int curdis){
		if(node == null)
			return null;
		if(discrim == curdis){
			if(node.left == null)
				return node.coordinate;
			else
				return findMin(node.left, discrim, (curdis + 1) % D);
		}else{
			return minimum(node.coordinate, 
					findMin(node.left, discrim, (curdis + 1) % D),
					findMin(node.right, discrim, (curdis + 1) % D),
					discrim);
		}
	}
	
	/** return the minimum discriminator **/
	private int[] minimum(int[] current, int[] minLeft, int[] minRight, int discrim){
		int[] min = current;
		if(min[discrim] > minLeft[discrim])
			min = minLeft;
		if(min[discrim] > minRight[discrim])
			min = minRight;
		return min;
	}
	
	/** find node in range **/
	public Stack<KDNode> findNode(int x, int y, int range){
		Stack<KDNode> nodeStack = new Stack<>();
		int[] coordinate = {x,y};
		findNode(coordinate, range, root, nodeStack, 0);
		return nodeStack;
	}
	
	private void findNode(int[] coordinate, int range, KDNode node, Stack<KDNode> nodeStack, int discrim){
		if(node == null) return;
		float d = (float) Math.sqrt(Math.pow(coordinate[0]-node.coordinate[0], 2) + Math.pow(coordinate[1]-node.coordinate[1], 2));
		if(d <= range){
			findNode(coordinate, range, node.left, nodeStack, (discrim + 1) % D);
			findNode(coordinate, range, node.right, nodeStack, (discrim + 1) % D);
			nodeStack.push(node);
		}else{
			if(node.coordinate[discrim] < coordinate[discrim] - range)
				findNode(coordinate, range, node.right, nodeStack, (discrim + 1) % D);
			else
				findNode(coordinate, range, node.left, nodeStack, (discrim + 1) % D);
		}
	}
	
}
