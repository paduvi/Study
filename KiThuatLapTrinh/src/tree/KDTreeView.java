package tree;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class KDTreeView extends JPanel {
    public final KDTree tree; // A binary tree to be displayed
    private final JTextField jtfX = new JTextField(5);
    private final JTextField jtfY = new JTextField(5);
    public final PaintKDTree paintTree = new PaintKDTree();
    private final JButton jbtInsert = new JButton("Insert");
    private final JButton jbtDelete = new JButton("Delete");
    private final JButton jbtClear = new JButton("Make Empty");
    private final JPanel findPanel = new JPanel();
    private final JLabel lblNewLabel = new JLabel("X:");
    private final JTextField xTxtField = new JTextField();
    private final JLabel lblX = new JLabel("Y:");
    private final JTextField yTxtField = new JTextField();
    private final JLabel lblY = new JLabel("D:");
    private final JTextField dTxtField = new JTextField();
    private final JButton findButton = new JButton("Search");
    private final JList<KDNode> nodeList = new JList<>();
    public final DefaultListModel<KDNode> listModel = new DefaultListModel<>();
    public JScrollPane scrollPane;
    private final JPanel listPanel = new JPanel();

    /**
     * Construct a view for a binary tree
     */
    public KDTreeView(KDTree tree) {
    	dTxtField.setColumns(10);
    	yTxtField.setColumns(10);
    	xTxtField.setColumns(10);
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
        	paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + tree.getDepth())) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
            scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, tree.getDepth() + 1) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
        }

        JPanel botPanel = new JPanel();
        botPanel.add(new JLabel("X: "));
        botPanel.add(jtfX);
        botPanel.add(new JLabel("Y: "));
        botPanel.add(jtfY);
        botPanel.add(jbtInsert);
        botPanel.add(jbtDelete);
        botPanel.add(jbtClear);
        add(botPanel, BorderLayout.SOUTH);
        
        add(findPanel, BorderLayout.EAST);
        findPanel.setLayout(new MigLayout("", "[grow]", "[][][][][grow][][]"));
        
        findPanel.add(lblNewLabel, "flowx,cell 0 0");
        
        findPanel.add(lblX, "flowx,cell 0 1");
        
        findPanel.add(lblY, "flowx,cell 0 2,growy");
        
        findPanel.add(xTxtField, "cell 0 0,growx");
        
        findPanel.add(yTxtField, "cell 0 1,growx");
        
        findPanel.add(dTxtField, "cell 0 2,growx");
        findButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String txtX = xTxtField.getText();
        		String txtY = yTxtField.getText();
        		String txtD = dTxtField.getText();
        		if(txtD.matches("\\d+$") == false){
        			JOptionPane.showMessageDialog(null, txtD + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		if(txtX.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtX + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
        		if(txtY.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtY + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
        		int d = Integer.parseInt(txtD);
        		int x = Integer.parseInt(txtX);
        		int y = Integer.parseInt(txtY);
        		listModel.clear();
        		Stack<KDNode> stackNode = tree.findNode(x,y,d);
        		while(!stackNode.isEmpty())
        			listModel.addElement(stackNode.pop());
        	}
        });
        
        findPanel.add(findButton, "cell 0 3,alignx center");
        
        JScrollPane listScrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        findPanel.add(listScrollPane, "flowy,cell 0 4 1 3,grow");
        listPanel.setLayout(new BorderLayout(0, 0));
        listPanel.add(nodeList);
        nodeList.setBackground(UIManager.getColor("Button.background"));
        nodeList.setModel(listModel);

        // Listener for the Insert button
        jbtInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String txtX = jtfX.getText();
            	String txtY = jtfY.getText();
            	if(txtX.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtX + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	if(txtY.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtY + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	int x = Integer.parseInt(txtX);
            	int y = Integer.parseInt(txtY);
                if (tree.search(x, y)) { // key is in the tree already
                    JOptionPane.showMessageDialog(null, "(" + x +"," + y + ") đã có trong cây", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    tree.insert(x, y); // Insert a new key
                    paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + tree.getDepth())) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
                    scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, tree.getDepth() + 1) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
                    paintTree.repaint(); // Redisplay the tree
                    scrollPane.updateUI(); // Update scroll bar
                }
                jtfX.setText("");
                jtfY.setText("");
            }
        });

        // Listener for the Delete button
        jbtDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String txtX = jtfX.getText();
            	String txtY = jtfY.getText();
            	if(txtX.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtX + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	if(txtY.matches("-?\\d+$") == false){
            		JOptionPane.showMessageDialog(null, txtY + " không phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	int x = Integer.parseInt(txtX);
            	int y = Integer.parseInt(txtY);
                if (!tree.search(x, y)) { // key is not in the tree
                    JOptionPane.showMessageDialog(null, "(" + x +"," + y + ") không có trong cây", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    tree.remove(x, y); // Delete a key
                    if (!tree.isEmpty()) {
                    	paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + tree.getDepth())) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
                        scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, tree.getDepth() + 1) * paintTree.getRadius(), (1 + tree.getDepth()) * paintTree.getVGap() + paintTree.getRadius()));
                    }
                    paintTree.repaint(); // Redisplay the tree
                    scrollPane.updateUI(); // Update scroll bar
                }
                jtfX.setText("");
                jtfY.setText("");
            }
        });

        // Listener for the Clear button
        jbtClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tree.clear();
                paintTree.repaint();
                scrollPane.updateUI();
            }
        });
    }

    // Inner class PaintTree for displaying a tree on a panel
    public class PaintKDTree extends JPanel {

        private int radius = 22; // Tree node radius
        private int vGap = 60; // Gap between two levels in a tree

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (tree.root != null) {
				// Display tree recursively    
                //				displayTree(g, tree.root, (int)Math.pow(2, tree.root.height - 1)*radius, 30, (int)Math.pow(2, tree.root.height-2)*radius); 
                displayTree(g, tree.root, Math.max((int) Math.pow(2, tree.getDepth()) * paintTree.radius, Frame.getFrames()[0].getWidth()/2), 30, (int) Math.pow(2, tree.getDepth() - 1) * radius);
            }
        }

        /**
         * Display a subtree rooted at position (x, y)
         */
        private void displayTree(Graphics g, KDNode root,
                int x, int y, int hGap) {
            if (root == null) {
                return;
            }
            g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g.drawString(root.coordinate[0] +";"+ root.coordinate[1], x - 16, y + 4);

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
