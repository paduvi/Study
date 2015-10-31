package tree;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class AVLTreeView extends JPanel {

    public final AVLTree<Integer> tree; // A binary tree to be displayed
    private final JTextField jtfKey = new JTextField(5);
    public final PaintAVLTree paintTree = new PaintAVLTree();
    private final JButton jbtInsert = new JButton("Insert");
    private final JButton jbtDelete = new JButton("Delete");
    private final JButton jbtClear = new JButton("Make Empty");
    public JScrollPane scrollPane;

    /**
     * Construct a view for a binary tree
     */
    public AVLTreeView(AVLTree<Integer> tree) {
        this.tree = tree; // Set a binary tree to be displayed
        setUI();
    }

    /**
     * Initialize UI for binary tree
     */
    private void setUI() {
        this.setLayout(new BorderLayout());
        this.scrollPane = new JScrollPane(paintTree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        if (!tree.isEmpty()) {
        	paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + tree.root.height)) * paintTree.getRadius(), (1 + tree.root.height) * paintTree.getVGap() + paintTree.getRadius()));
            scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, tree.root.height + 1) * paintTree.getRadius(), (1 + tree.root.height) * paintTree.getVGap() + paintTree.getRadius()));
        }

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter a key: "));
        panel.add(jtfKey);
        panel.add(jbtInsert);
        panel.add(jbtDelete);
        panel.add(jbtClear);
        add(panel, BorderLayout.SOUTH);

        // Listener for the Insert button
        jbtInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String txtKey = jtfKey.getText();
            	if(txtKey.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtKey + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
                int key = Integer.parseInt(txtKey);
                if (tree.search(key)) { // key is in the tree already
                    JOptionPane.showMessageDialog(null, key + " đã có trong cây", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    tree.insert(key); // Insert a new key
                    paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + tree.root.height)) * paintTree.getRadius(), (1 + tree.root.height) * paintTree.getVGap() + paintTree.getRadius()));
                    scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, tree.root.height + 1) * paintTree.getRadius(), (1 + tree.root.height) * paintTree.getVGap() + paintTree.getRadius()));
                    paintTree.repaint(); // Redisplay the tree
                    scrollPane.updateUI();
                }
                jtfKey.setText("");
            }
        });

        // Listener for the Delete button
        jbtDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String txtKey = jtfKey.getText();
            	if(txtKey.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtKey + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
                int key = Integer.parseInt(txtKey);
                if (!tree.search(key)) { // key is not in the tree
                    JOptionPane.showMessageDialog(null, key + " không có trong cây", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    tree.remove(key); // Delete a key
                    if (!tree.isEmpty()) {
                    	paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + tree.root.height)) * paintTree.getRadius(), (1 + tree.root.height) * paintTree.getVGap() + paintTree.getRadius()));
                        scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, tree.root.height + 1) * paintTree.getRadius(), (1 + tree.root.height) * paintTree.getVGap() + paintTree.getRadius()));
                    }
                    paintTree.repaint(); // Redisplay the tree
                    scrollPane.updateUI();
                }
                jtfKey.setText("");
            }
        });

        // Listener for the Delete button
        jbtClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tree.makeEmpty();
                paintTree.repaint();
                scrollPane.updateUI();
            }
        });
    }

    // Inner class PaintTree for displaying a tree on a panel
    public class PaintAVLTree extends JPanel {

        private int radius = 20; // Tree node radius
        private int vGap = 50; // Gap between two levels in a tree

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (tree.root != null) {
				// Display tree recursively    
                //				displayTree(g, tree.root, (int)Math.pow(2, tree.root.height - 1)*radius, 30, (int)Math.pow(2, tree.root.height-2)*radius); 
                displayTree(g, tree.root, Math.max((int) Math.pow(2, tree.root.height) * paintTree.radius, Frame.getFrames()[0].getWidth()/2), 30, (int) Math.pow(2, tree.root.height - 1) * radius);
            }
        }

        /**
         * Display a subtree rooted at position (x, y)
         */
        private void displayTree(Graphics g, AVLNode<Integer> root,
                int x, int y, int hGap) {
            if (root == null) {
                return;
            }
            g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g.drawString(root.data + "", x - 6, y + 4);

            if (root.left != null) {
                // Draw a line to the left node
                connectLeftChild(g, x - hGap, y + vGap, x, y);
                // Draw the left subtree recursively
                displayTree(g, root.left, x - hGap, y + vGap, hGap / 2);
            }

            if (root.right != null) {
                // Draw a line to the right node
                connectRightChild(g, x + hGap, y + vGap, x, y);
                // Draw the right subtree recursively
                displayTree(g, root.right, x + hGap, y + vGap, hGap / 2);
            }
        }

        public int getRadius() {
            return this.radius;
        }

        public int getVGap() {
            return this.vGap;
        }

        /**
         * Connect a parent at (x2, y2) with its left child at (x1, y1)
         */
        private void connectLeftChild(Graphics g,
                int x1, int y1, int x2, int y2) {
//			g.setColor(Color.BLACK);
            double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
            int x11 = (int) (x1 + radius * (x2 - x1) / d);
            int y11 = (int) (y1 - radius * vGap / d);
            int x21 = (int) (x2 - radius * (x2 - x1) / d);
            int y21 = (int) (y2 + radius * vGap / d);
            g.drawLine(x11, y11, x21, y21);
        }

        /**
         * Connect a parent at (x2, y2) with its right child at (x1, y1)
         */
        private void connectRightChild(Graphics g,
                int x1, int y1, int x2, int y2) {
            double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
            int x11 = (int) (x1 - radius * (x1 - x2) / d);
            int y11 = (int) (y1 - radius * vGap / d);
            int x21 = (int) (x2 + radius * (x1 - x2) / d);
            int y21 = (int) (y2 + radius * vGap / d);
            g.drawLine(x11, y11, x21, y21);
        }
    }
}
