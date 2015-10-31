package tree;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;

import javax.swing.*;

@SuppressWarnings("serial")
public class TriesView extends JPanel {

    public final Huffman tree; // A binary tree to be displayed
    public final PaintTries paintTree = new PaintTries();
    private JTextField pathTxt;
    public HuffmanNode curNode;
    private final Stack<String> txtStack = new Stack<>();
    public JScrollPane scrollPane;
    /**
     * Construct a view for a binary tree
     * @param tree
     */
    public TriesView(Huffman tree) {
        this.tree = tree; // Set a binary tree to be displayed
        curNode = tree.root;
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
        	paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + tree.getDepth())) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
            scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, tree.getDepth() + 1) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
        }
        JPanel panel = new JPanel();
        pathTxt = new JTextField(10);
        panel.add(new JLabel("Enter key path: "));
        panel.add(pathTxt);
        add(panel, BorderLayout.SOUTH);

        pathTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                String txt = pathTxt.getText();
                txtStack.push(txt);
            }

            public void keyReleased(KeyEvent evt) {
                String txt = pathTxt.getText();
                String oldTxt = pathTxt.getText();
                if (!txtStack.isEmpty()) {
                    oldTxt = txtStack.pop();
                }
                if (txt.matches("^[01]*$") != true) {
                    pathTxt.setText(oldTxt);
                    JOptionPane.showMessageDialog(null, "Ký tự không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                HuffmanNode temp = tree.root;
                for (int i = 0; i < txt.length() && temp != null; i++) {
                    if (txt.charAt(i) == '0') {
                        temp = temp.left;
                    } else if (txt.charAt(i) == '1') {
                        temp = temp.right;
                    }
                }
                if (temp == null) {
                    JOptionPane.showMessageDialog(null, "Không có nút lá tương ứng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    pathTxt.setText(oldTxt);
                    return;
                }
                curNode = temp;
                paintTree.repaint();
            }
        });
    }

    // Inner class PaintTree for displaying a tree on a panel
    public class PaintTries extends JPanel {

        /**
         *
         */
        private int radius = 20; // Tree node radius
        private int vGap = 50; // Gap between two levels in a tree

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (tree != null) {
				// Display tree recursively    
                //				displayTree(g, tree.root, (int)Math.pow(2, tree.root.height - 1)*radius, 30, (int)Math.pow(2, tree.root.height-2)*radius); 
                displayTree(g, tree.root, getWidth() / 2, 30, getWidth() / 4);
            }
        }

        /**
         * Display a subtree rooted at position (x, y)
         */
        private void displayTree(Graphics g, HuffmanNode root,
                int x, int y, int hGap) {
            // Display the root
            if (root == null) {
                return;
            }
            Graphics2D ga = (Graphics2D) g;
            if (curNode == root) {
                ga.setPaint(Color.RED);
            } else {
                ga.setPaint(Color.BLACK);
            }
            ga.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
            ga.setPaint(Color.BLACK);
            ga.setColor(Color.WHITE);
            ga.drawString(String.valueOf(root.frequency), x - 6, y + 4);
            ga.setColor(Color.RED);
            ga.drawString(String.valueOf(root.value), x - 6, y + 36);
            ga.setColor(Color.BLACK);

            if (root.left != null) {
                // Draw a line to the left node
                connectLeftChild(g, x - hGap, y + vGap, x, y);
                ga.drawString("0", x - hGap / 2 - 12, y + vGap / 2);
                displayTree(g, root.left, x - hGap, y + vGap, Math.max(hGap / 2, radius));
            }

            if (root.right != null) {
                // Draw a line to the right node
                connectRightChild(g, x + hGap, y + vGap, x, y);
                ga.drawString("1", x + hGap / 2 + 5, y + vGap / 2);
                // Draw the right subtree recursively
                displayTree(g, root.right, x + hGap, y + vGap, Math.max(hGap / 2, radius));
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
