package tree;

import java.util.*;

class HuffmanNode implements Comparable<HuffmanNode> {

    public HuffmanNode left, right; // subtrees
    public int frequency;
    public char value;

    public HuffmanNode(HuffmanNode l, HuffmanNode r) {
        frequency = l.frequency + r.frequency;
        left = l;
        right = r;
        value = ' ';
    }

    public HuffmanNode(int freg) {
        frequency = freg;
        value = ' ';
    }

    public HuffmanNode(int freg, char val) {
        frequency = freg;
        value = val;
    }

    @Override
    public int compareTo(HuffmanNode tree) {
        return frequency - tree.frequency;
    }
}

public class Huffman {

    // input is an array of frequencies, indexed by character code

    HuffmanNode root;

    public Huffman(int[] charFreqs, char[] charAry) {
        root = this.buildTree(charFreqs, charAry);
    }

    public Huffman() {
        root = null;
    }

    public HuffmanNode buildTree(int[] charFreqs, char[] charAry) {
        PriorityQueue<HuffmanNode> trees = new PriorityQueue<>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++) {
            if (charFreqs[i] > 0) {
                trees.offer(new HuffmanNode(charFreqs[i], charAry[i]));
            }
        }

        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanNode a = trees.poll();
            HuffmanNode b = trees.poll();

            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        root = trees.poll();
        return root;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public int getDepth() {
        return getDepth(root);
    }

    private int getDepth(HuffmanNode r) {
        if (r == null) {
            return -1;
        }
        return 1 + Math.max(getDepth(r.left), getDepth(r.right));
    }
}
