package tree;

import java.util.Stack;

class PrNode{
	PrNode NW, NE, SW, SE;
	int[] coordinate = new int[2];

	public PrNode(){
		this.NW = null;
		this.NE = null;
		this.SW = null;
		this.SE = null;
	}

	public PrNode(int x, int y){
		this.coordinate[0] = x;
		this.coordinate[1] = y;
		this.NW = null;
		this.NE = null;
		this.SW = null;
		this.SE = null;
	}

	@Override
	public String toString(){
		return "("+ this.coordinate[0] +"; " + this.coordinate[1] + ")";
	}
}
public class PrQuadTree {
	final int MAX_X = 128;
	final int MAX_Y = 128;
	final int MIN_X = 0;
	final int MIN_Y = 0;
	PrNode root;

	public PrQuadTree(){
		root = null;
	}

	/** check empty **/
	public boolean isEmpty(){
		return root == null;
	}

	/** get depth **/
	public int getDepth(){
		return getDepth(root);
	}

	private int getDepth(PrNode node){
		if(node == null)
			return -1;
		return 1 + Math.max(Math.max(getDepth(node.NE), getDepth(node.NW)), Math.max(getDepth(node.SE), getDepth(node.SW)));
	}

	/** clear **/
	public void clear(){
		root = null;
	}

	/** check is leaf node **/
	boolean isLeaf(PrNode node){
		if(node == null)
			return false;
		if(node.NE != null)
			return false;
		if(node.SE != null)
			return false;
		if(node.NW != null)
			return false;
		if(node.SW != null)
			return false;
		return true;
	}

	/** insert new node **/
	public void insert(int x, int y){
		if(search(x,y))
			return;
		root = insert(root, x, y, MIN_X, MIN_Y, MAX_X, MAX_Y);
	}

	private PrNode insert(PrNode node, int x, int y, int MIN_X, int MIN_Y, int MAX_X, int MAX_Y){
		if(node == null)
			return new PrNode(x, y);
		if(isLeaf(node)){
			PrNode temp = node;
			node = new PrNode();
			if((temp.coordinate[0] < (MAX_X + MIN_X)/2) && (temp.coordinate[1] < (MAX_Y + MIN_Y)/2)){
				node.SW = temp;
			} else if((temp.coordinate[0] < (MAX_X + MIN_X)/2) && !(temp.coordinate[1] < (MAX_Y + MIN_Y)/2)){
				node.NW = temp;
			} else if(!(temp.coordinate[0] < (MAX_X + MIN_X)/2) && (temp.coordinate[1] < (MAX_Y + MIN_Y)/2)){
				node.SE = temp;
			} else{
				node.NE = temp;
			}
		}
		if((x < (MAX_X + MIN_X)/2) && (y < (MAX_Y + MIN_Y)/2)){
			node.SW = insert(node.SW, x, y, MIN_X, MIN_Y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2);
		} else if((x < (MAX_X + MIN_X)/2) && !(y < (MAX_Y + MIN_Y)/2)){
			node.NW = insert(node.NW, x, y, MIN_X, (MAX_Y + MIN_Y)/2, (MAX_X + MIN_X)/2, MAX_Y);
		} else if(!(x < (MAX_X + MIN_X)/2) && (y < (MAX_Y + MIN_Y)/2)){
			node.SE = insert(node.SE, x, y, (MAX_X + MIN_X)/2, MIN_Y, MAX_X, (MAX_Y + MIN_Y)/2);
		} else{
			node.NE = insert(node.NE, x, y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2, MAX_X, MAX_Y);
		}
		return node;
	}

	/** search **/
	public boolean search(int x, int y){
		return search(root, x, y, MIN_X, MIN_Y, MAX_X, MAX_Y);
	}

	private boolean search(PrNode node, int x, int y, int MIN_X, int MIN_Y, int MAX_X, int MAX_Y){
		if(node == null)
			return false;
		if(isLeaf(node)){
			if(x == node.coordinate[0] && y == node.coordinate[1])
				return true;
			else
				return false;
		}
		if((x < (MAX_X + MIN_X)/2) && (y < (MAX_Y + MIN_Y)/2)){
			return search(node.SW, x, y, MIN_X, MIN_Y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2);
		} else if((x < (MAX_X + MIN_X)/2) && !(y < (MAX_Y + MIN_Y)/2)){
			return search(node.NW, x, y, MIN_X, (MAX_Y + MIN_Y)/2, (MAX_X + MIN_X)/2, MAX_Y);
		} else if(!(x < (MAX_X + MIN_X)/2) && (y < (MAX_Y + MIN_Y)/2)){
			return search(node.SE, x, y, (MAX_X + MIN_X)/2, MIN_Y, MAX_X, (MAX_Y + MIN_Y)/2);
		} else{
			return search(node.NE, x, y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2, MAX_X, MAX_Y);
		}
	}

	/** count leaf **/
	private int countLeaf(PrNode node){
		if(node == null)
			return 0;
		if(isLeaf(node))
			return 1;
		return countLeaf(node.NE) + countLeaf(node.NW) + countLeaf(node.SE) + countLeaf(node.SW);
	}

	/** remove **/
	public void remove(int x, int y){
		if(!search(x,y))
			return;
		root = remove(root, x, y, MIN_X, MIN_Y, MAX_X, MAX_Y);
	}

	private PrNode remove(PrNode node, int x, int y, int MIN_X, int MIN_Y, int MAX_X, int MAX_Y){
		if(node == null)
			return null;
		if(isLeaf(node)){
			if(x == node.coordinate[0] && y == node.coordinate[1])
				return null;
			else
				return node;
		}
		if((x < (MAX_X + MIN_X)/2) && (y < (MAX_Y + MIN_Y)/2)){
			node.SW = remove(node.SW, x, y, MIN_X, MIN_Y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2);
		} else if((x < (MAX_X + MIN_X)/2) && !(y < (MAX_Y + MIN_Y)/2)){
			node.NW = remove(node.NW, x, y, MIN_X, (MAX_Y + MIN_Y)/2, (MAX_X + MIN_X)/2, MAX_Y);
		} else if(!(x < (MAX_X + MIN_X)/2) && (y < (MAX_Y + MIN_Y)/2)){
			node.SE = remove(node.SE, x, y, (MAX_X + MIN_X)/2, MIN_Y, MAX_X, (MAX_Y + MIN_Y)/2);
		} else{
			node.NE = remove(node.NE, x, y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2, MAX_X, MAX_Y);
		}
		node = merge(node);	
		return node;
	}

	/** merge **/
	private PrNode merge(PrNode node){
		if(node == null)
			return null;
		if(node.SW == null && node.NW == null && node.SE == null && node.NE == null){
			return null;
		}
		if(countLeaf(node) > 1)
			return node;
		if(node.SW != null)
			node = node.SW;
		if(node.NW != null)
			node = node.NW;
		if(node.NE != null)
			node = node.NE;
		if(node.SE != null)
			node = node.SE;
		node.SE = null;
		node.NE = null;
		node.NW = null;
		node.SW = null;
		return node;
	}

	/** find node in range **/
	public Stack<PrNode> findNode(int x, int y, int range){
		Stack<PrNode> nodeStack = new Stack<>();
		findNode(x, y, range, root, nodeStack, MIN_X, MIN_Y, MAX_X, MAX_Y);
		return nodeStack;
	}

	private void findNode(int x, int y, int range, PrNode node, Stack<PrNode> nodeStack, int MIN_X, int MIN_Y, int MAX_X, int MAX_Y){
		if(node == null) return;
		float d = (float) Math.sqrt(Math.pow(x-node.coordinate[0], 2) + Math.pow(y-node.coordinate[1], 2));
		if(intersect(x, y, range, MIN_X, (MAX_Y + MIN_Y)/2, (MAX_X + MIN_X)/2, MAX_Y))
			findNode(x, y, range, node.NW, nodeStack, MIN_X, (MAX_Y + MIN_Y)/2, (MAX_X + MIN_X)/2, MAX_Y);
		if(intersect(x, y, range, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2, MAX_X, MAX_Y))
			findNode(x, y, range, node.NE, nodeStack, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2, MAX_X, MAX_Y);
		if(intersect(x, y, range, MIN_X, MIN_Y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2))
			findNode(x, y, range, node.SW, nodeStack, MIN_X, MIN_Y, (MAX_X + MIN_X)/2, (MAX_Y + MIN_Y)/2);
		if(intersect(x, y, range, (MAX_X + MIN_X)/2, MIN_Y, MAX_X, (MAX_Y + MIN_Y)/2))
			findNode(x, y, range, node.SE, nodeStack, (MAX_X + MIN_X)/2, MIN_Y, MAX_X, (MAX_Y + MIN_Y)/2);
		if(d <= range)
			nodeStack.push(node);
	}
	
	/** check intersect **/
	private boolean intersect(int x, int y, int range, int MIN_X, int MIN_Y, int MAX_X, int MAX_Y){
		float centerX = (MIN_X + MAX_X)/2;
		float centerY = (MIN_Y + MAX_Y)/2;
		float distance = (float) Math.sqrt((Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)));
		float d = (float) Math.sqrt((Math.pow((MIN_X - MAX_X)/2, 2) + Math.pow((MIN_Y - MAX_Y)/2, 2)));
		if(distance > d + range)
			return false;
		return true;
	}
}
