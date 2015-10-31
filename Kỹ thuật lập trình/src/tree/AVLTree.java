package tree;

class AVLNode<T extends Comparable<T>> {

    AVLNode<T> left, right;
    T data;
    int height;

    /* Constructor */
    public AVLNode(T n) {
        left = null;
        right = null;
        data = n;
        height = 0;
    }
}

/* Class AVLTree */
public class AVLTree<T extends Comparable<T>> {

    AVLNode<T> root;

    /* Constructor */
    public AVLTree() {
        root = null;
    }

    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return root == null;
    }

    /* Make the tree logically empty */
    public void makeEmpty() {
        root = null;
    }

    /* Function to get height of tree */
    public int height() {
        return root.height;
    }

    /* Function to get height of node */
    private int height(AVLNode<T> t) {
        return t == null ? -1 : t.height;
    }

    /* Function to max of left/right node */
    private int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AVLNode<T> balance(AVLNode<T> t) {
        if (t == null) {
            return t;
        }

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right.right) >= height(t.right.left)) {
                t = rotateWithRightChild(t);
            } else {
                t = doubleWithRightChild(t);
            }
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /* Rotate binary tree node with left child */
    private AVLNode<T> rotateWithLeftChild(AVLNode<T> k2) {
        AVLNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /* Rotate binary tree node with right child */
    private AVLNode<T> rotateWithRightChild(AVLNode<T> k1) {
        AVLNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child with its right child;
     * then node k3 with new left child
     */
    private AVLNode<T> doubleWithLeftChild(AVLNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child with its left child;
     * then node k1 with new right child
     */
    private AVLNode<T> doubleWithRightChild(AVLNode<T> k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    /*Function to find max, min of tree*/
    public T findMin() {
        if (isEmpty()) {
            return null;
        }

        return findMin(root).data;
    }

    public T findMax() {
        if (isEmpty()) {
            return null;
        }
        return findMax(root).data;
    }

    /*Function to find max, min of node*/
    private AVLNode<T> findMax(AVLNode<T> t) {
        if (t == null) {
            return t;
        } else {
            while (t.right != null) {
                t = t.right;
            }
            return t;
        }
    }

    private AVLNode<T> findMin(AVLNode<T> t) {
        if (t == null) {
            return t;
        } else {
            while (t.left != null) {
                t = t.left;
            }
            return t;
        }
    }

    /* Function to insert data */
    public void insert(T data) {
        root = insert(data, root);
    }

    /* Function to insert data recursively */
    private AVLNode<T> insert(T x, AVLNode<T> t) {
        if (t == null) {
            return new AVLNode<>(x);
        }

        int compareResult = x.compareTo(t.data);

        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else
			;  // Duplicate; do nothing
        return balance(t);
    }

    /* Functions to count number of nodes */
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(AVLNode<T> r) {
        if (r == null) {
            return 0;
        } else {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }

    /* Functions to delete a node */
    public void remove(T x) {
        root = remove(x, root);
    }

    private AVLNode<T> remove(T x, AVLNode<T> t) {
        if (t == null) {
            return t;   // Item not found; do nothing
        }
        int compareResult = x.compareTo(t.data);

        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) // Two children
        {
            t.data = findMin(t.right).data;
            t.right = remove(t.data, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return balance(t);
    } //End of remove...

    /* Functions to search for an element */
    public boolean search(T val) {
        return search(root, val);
    }

    private boolean search(AVLNode<T> r, T val) {
        boolean found = false;
        while ((r != null) && !found) {
            T rval = r.data;
            if (val.compareTo(rval) < 0) {
                r = r.left;
            } else if (val.compareTo(rval) > 0) {
                r = r.right;
            } else {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    /* Function for inorder traversal */
    public void inorder() {
        inorder(root);
    }

    private void inorder(AVLNode<T> r) {
        if (r != null) {
            inorder(r.left);
            System.out.print(r.data + " ");
            inorder(r.right);
        }
    }

    /* Function for preorder traversal */
    public void preorder() {
        preorder(root);
    }

    private void preorder(AVLNode<T> r) {
        if (r != null) {
            System.out.print(r.data + " ");
            preorder(r.left);
            preorder(r.right);
        }
    }

    /* Function for postorder traversal */
    public void postorder() {
        postorder(root);
    }

    private void postorder(AVLNode<T> r) {
        if (r != null) {
            postorder(r.left);
            postorder(r.right);
            System.out.print(r.data + " ");
        }
    }
}
