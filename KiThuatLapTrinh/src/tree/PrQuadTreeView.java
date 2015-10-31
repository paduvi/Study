package tree;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PrQuadTreeView extends JPanel {
	public final PrQuadTree tree; // A binary tree to be displayed
	private final JTextField jtfX = new JTextField(5);
	private final JTextField jtfY = new JTextField(5);
	public final JPanel paintTree = new JPanel();
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
	private final JList<PrNode> nodeList = new JList<>();
	public final DefaultListModel<PrNode> listModel = new DefaultListModel<>();
	public final JTree jTree = new JTree();
	public JScrollPane scrollPane;
	private final JPanel listPanel = new JPanel();

	/**
	 * Construct a view for a binary tree
	 */
	public PrQuadTreeView(PrQuadTree tree) {
		dTxtField.setColumns(10);
		yTxtField.setColumns(10);
		xTxtField.setColumns(10);
		this.tree = tree;
		jTree.setShowsRootHandles(true);
		jTree.setCellRenderer(new TreeRenderer());
		setUI();
	}

	/**
	 * Build Tree
	 */
	public DefaultMutableTreeNode buildTree(PrNode node, String quad){
		DefaultMutableTreeNode treeNode;
		if(node == null)
			return new DefaultMutableTreeNode();
		if(quad == null)
			treeNode = new DefaultMutableTreeNode("Tree Root");
		else{
			if(tree.isLeaf(node))
				treeNode = new DefaultMutableTreeNode(quad + ": " + node.toString());
			else
				treeNode = new DefaultMutableTreeNode(quad);
		}
		if(node.NW != null)
			treeNode.add(buildTree(node.NW, "NW"));
		if(node.NE != null)
			treeNode.add(buildTree(node.NE, "NE"));
		if(node.SW != null)
			treeNode.add(buildTree(node.SW, "SW"));
		if(node.SE != null)
			treeNode.add(buildTree(node.SE, "SE"));
		return treeNode;	
	}

	/**
	 * Initialize UI for binary tree
	 */
	private void setUI() {
		this.setLayout(new BorderLayout());
		this.scrollPane = new JScrollPane(paintTree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
		GridBagLayout gbl_paintTree = new GridBagLayout();
		gbl_paintTree.columnWidths = new int[]{72, 0};
		gbl_paintTree.rowHeights = new int[]{32, 0};
		gbl_paintTree.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_paintTree.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		paintTree.setLayout(gbl_paintTree);

		GridBagConstraints gbc_jTree = new GridBagConstraints();
		gbc_jTree.fill = GridBagConstraints.BOTH;
		gbc_jTree.gridx = 0;
		gbc_jTree.gridy = 0;

		paintTree.add(jTree, gbc_jTree);

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
				if(x < tree.MIN_X || x > tree.MAX_X){
					JOptionPane.showMessageDialog(null, x + " không hợp lệ (" + tree.MIN_X +" <= X <= " + tree.MAX_X + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(y < tree.MIN_Y || y > tree.MAX_Y){
					JOptionPane.showMessageDialog(null, y + " không hợp lệ (" + tree.MIN_Y +" <= Y <= " + tree.MAX_Y + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				listModel.clear();
				Stack<PrNode> stackNode = tree.findNode(x,y,d);
				while(!stackNode.isEmpty())
					listModel.addElement(stackNode.pop());
			}
		});

		findPanel.add(findButton, "cell 0 3,alignx center");
		JScrollPane listScrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		findPanel.add(listScrollPane, "flowx,cell 0 4 1 3,grow");
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
				if(x < tree.MIN_X || x > tree.MAX_X){
					JOptionPane.showMessageDialog(null, x + " không hợp lệ (" + tree.MIN_X +" <= X <= " + tree.MAX_X + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(y < tree.MIN_Y || y > tree.MAX_Y){
					JOptionPane.showMessageDialog(null, y + " không hợp lệ (" + tree.MIN_Y +" <= Y <= " + tree.MAX_Y + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (tree.search(x, y)) { // key is in the tree already
					JOptionPane.showMessageDialog(null, "(" + x +"," + y + ") đã có trong cây", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} else {
					tree.insert(x, y); // Insert a new key
					jTree.setModel(new DefaultTreeModel(buildTree(tree.root, null)));
					for (int i = 0; i < jTree.getRowCount(); i++) {
						jTree.expandRow(i);
					}
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
				if(x < tree.MIN_X || x > tree.MAX_X){
					JOptionPane.showMessageDialog(null, x + " không hợp lệ (" + tree.MIN_X +" <= X <= " + tree.MAX_X + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(y < tree.MIN_Y || y > tree.MAX_Y){
					JOptionPane.showMessageDialog(null, y + " không hợp lệ (" + tree.MIN_Y +" <= Y <= " + tree.MAX_Y + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!tree.search(x, y)) { // key is not in the tree
					JOptionPane.showMessageDialog(null, "(" + x +"," + y + ") không có trong cây", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} else {
					tree.remove(x, y); // Delete a key
					jTree.setModel(new DefaultTreeModel(buildTree(tree.root, null)));
					for (int i = 0; i < jTree.getRowCount(); i++) {
						jTree.expandRow(i);
					}
				}
				jtfX.setText("");
				jtfY.setText("");
			}
		});

		// Listener for the Clear button
		jbtClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tree.clear();
				jTree.setModel(new DefaultTreeModel(buildTree(tree.root, null)));
			}
		});
	}

}

@SuppressWarnings("serial") 
class TreeRenderer extends DefaultTreeCellRenderer {

	private static final Icon closed = new ImageIcon(TreeApp.class.getResource("/tree/plus-4-xxl_15x15.png"));
	private static final Icon open = new ImageIcon(TreeApp.class.getResource("/tree/circle-minus-512_15x15.png"));
	private static final Icon leaf = new ImageIcon(TreeApp.class.getResource("/tree/GreenSENSE-Leaf_15x15.png"));

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean exp, boolean isLeaf, int row, boolean hasFocus) {
		setOpenIcon(open);
		setClosedIcon(closed);
		setLeafIcon(leaf);
		super.getTreeCellRendererComponent(
				tree, value, sel, exp, isLeaf, row, hasFocus);
		return this;
	}
}
