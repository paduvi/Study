package tree;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultTreeModel;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TreeApp {

	private JFrame frmAdvancedTree;
	private JPanel mainPanel;
	private AVLTreeView avlPanel;
	private JPanel prevAvlPanel;
	private JPanel prevSplayPanel;
	private JPanel prevTriesPanel;
	private JPanel prevKdPanel;
	private JPanel prevPrPanel;
	private JPanel prevHuffmanPanel;
	private TriesView triesPanel;
	private JButton btnAvlPrevButton;
	private JButton btnSplayPrevButton;
	private JButton btnTriesPrevButton;
	private JButton btnKdPrevButton;
	private JButton btnPrPrevButton;
	private JButton btnHuffmanPrevButton;
	private JPanel huffmanPanel;
	private JPanel introPanel;
	private JTextPane txtpnNhpVoGi;
	private JPanel huffmanBodyPanel;
	private JPanel frequencyForm;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	private JLabel lblE;
	private JLabel lblF;
	private JLabel lblG;
	private JTextField aTextField;
	private JTextField bTextField;
	private JTextField cTextField;
	private JTextField dTextField;
	private JTextField eTextField;
	private JTextField fTextField;
	private JTextField gTextField;
	private JButton btnGenerateButton;
	private JPanel layout;
	private SplayTreeView splayPanel;
	private JLabel lblBackground;
	private JButton btnTriesButton;
	private JButton btnAVLButton;
	private JButton btnSplayButton;
	private JLabel lblSponsor;
	private JLabel lblNhom;
	private JButton btnKDButton;
	private KDTreeView kdPanel;
	private JButton btnPRButton;
	private PrQuadTreeView prPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreeApp window = new TreeApp();
					window.frmAdvancedTree.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws HeadlessException 
	 */
	public TreeApp() throws HeadlessException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws HeadlessException 
	 */
	@SuppressWarnings("serial")
	private void initialize() throws HeadlessException, IOException {
		frmAdvancedTree = new JFrame();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(TreeApp.class.getResource("/tree/chotoxautinh.jpg"));
		frmAdvancedTree.setIconImage(img);
		frmAdvancedTree.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				btnTriesButton.setBounds(frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10, frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10);
				btnAVLButton.setBounds(frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10 + frmAdvancedTree.getHeight()/10 + frmAdvancedTree.getHeight()/40, frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10);
				btnSplayButton.setBounds(frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10 + 2 * (frmAdvancedTree.getHeight()/10 + frmAdvancedTree.getHeight()/40), frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10);
				btnKDButton.setBounds(frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10 + 3 * (frmAdvancedTree.getHeight()/10 + frmAdvancedTree.getHeight()/40), frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10);
				btnPRButton.setBounds(frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10 + 4 * (frmAdvancedTree.getHeight()/10 + frmAdvancedTree.getHeight()/40), frmAdvancedTree.getWidth()/3, frmAdvancedTree.getHeight()/10);
				lblBackground.setSize(frmAdvancedTree.getWidth(), frmAdvancedTree.getHeight());
				lblNhom.setBounds(frmAdvancedTree.getWidth()/3, 7 * frmAdvancedTree.getHeight()/10, frmAdvancedTree.getWidth()/3, 30);
				lblSponsor.setBounds(frmAdvancedTree.getWidth()/3, 7 * frmAdvancedTree.getHeight()/10 + frmAdvancedTree.getHeight()/20, frmAdvancedTree.getWidth()/3, 20);
				lblBackground.repaint();
			}
		});
		frmAdvancedTree.setTitle("Advanced Tree - Nhóm 5 KSTN CNTT K58");
		frmAdvancedTree.setBounds(100, 100, 700, 600);
		frmAdvancedTree.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdvancedTree.getContentPane().setLayout(new BorderLayout(0, 0));

		Random r = new Random();

		Huffman huffman = new Huffman();

		java.util.ArrayList<JTextField> txtFieldAry = new java.util.ArrayList<JTextField>();

		layout = new JPanel();
		frmAdvancedTree.getContentPane().add(layout);
		layout.setLayout(new CardLayout(0, 0));

		mainPanel = new JPanel();
		mainPanel.setBorder(null);
		layout.add(mainPanel, "name_191048157068979");
		mainPanel.setLayout(null);

		btnTriesButton = new JButton("Tries");
		btnTriesButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/1_50x50.png")));
		mainPanel.add(btnTriesButton);
		btnTriesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				huffmanPanel.setVisible(true);
			}
		});
		btnTriesButton.setFont(new Font("Tahoma", Font.BOLD, 13));

		AVLTree<Integer> avlTree = new AVLTree<>();
		btnAVLButton = new JButton("AVL Tree");
		btnAVLButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/8_50x50.png")));
		mainPanel.add(btnAVLButton);
		btnAVLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				for (int i = 0; i < 20; i++) {
					avlTree.insert(r.nextInt(200)-100);
				}
				avlPanel.paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + avlPanel.tree.root.height)) * avlPanel.paintTree.getRadius(), (1 + avlPanel.tree.root.height) * avlPanel.paintTree.getVGap() + avlPanel.paintTree.getRadius()));
				avlPanel.scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, avlPanel.tree.root.height + 1) * avlPanel.paintTree.getRadius(), (1 + avlPanel.tree.root.height) * avlPanel.paintTree.getVGap() + avlPanel.paintTree.getRadius()));
				avlPanel.paintTree.repaint(); // Redisplay the tree
				avlPanel.scrollPane.updateUI();
				avlPanel.setVisible(true);
			}
		});
		btnAVLButton.setFont(new Font("Tahoma", Font.BOLD, 13));

		SplayTree<Integer> splayTree = new SplayTree<>();
		btnSplayButton = new JButton("Splay Tree");
		btnSplayButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/24_50x50.png")));
		mainPanel.add(btnSplayButton);
		btnSplayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				for (int i = 0; i < 9; i++) {
					splayTree.insert(r.nextInt(200)-100);
				}
				splayPanel.paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + splayPanel.tree.getDepth())) * splayPanel.paintTree.getRadius(), (1 + splayPanel.tree.getDepth()) * splayPanel.paintTree.getVGap() + splayPanel.paintTree.getRadius()));
				splayPanel.scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, splayPanel.tree.getDepth() + 1) * splayPanel.paintTree.getRadius(), (1 + splayPanel.tree.getDepth()) * splayPanel.paintTree.getVGap() + splayPanel.paintTree.getRadius()));
				splayPanel.paintTree.repaint(); // Redisplay the tree
				splayPanel.scrollPane.updateUI(); // Update scroll bar
				splayPanel.setVisible(true);
			}
		});
		btnSplayButton.setFont(new Font("Tahoma", Font.BOLD, 13));

		KDTree kdTree = new KDTree();
		btnKDButton = new JButton("KD Tree");
		btnKDButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/9_50x50.png")));
		btnKDButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				for (int i = 0; i < 8; i++) {
					kdTree.insert(r.nextInt(100), r.nextInt(100));
				}
				kdPanel.paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + kdPanel.tree.getDepth())) * kdPanel.paintTree.getRadius(), (1 + kdPanel.tree.getDepth()) * kdPanel.paintTree.getVGap() + kdPanel.paintTree.getRadius()));
				kdPanel.scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, kdPanel.tree.getDepth() + 1) * kdPanel.paintTree.getRadius(), (1 + kdPanel.tree.getDepth()) * kdPanel.paintTree.getVGap() + kdPanel.paintTree.getRadius()));
				kdPanel.paintTree.repaint(); // Redisplay the tree
				kdPanel.scrollPane.updateUI(); // Update scroll bar
				kdPanel.setVisible(true);
			}
		});
		btnKDButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		mainPanel.add(btnKDButton);

		PrQuadTree prTree = new PrQuadTree();
		btnPRButton = new JButton("PR Quad Tree");
		btnPRButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/17_50x50.png")));
		btnPRButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnPRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				for (int i = 0; i < 20; i++) {
					prTree.insert(r.nextInt(128), r.nextInt(128));
				}
				prPanel.jTree.setModel(new DefaultTreeModel(prPanel.buildTree(prPanel.tree.root, null)));
				for (int i = 0; i < prPanel.jTree.getRowCount(); i++) {
					prPanel.jTree.expandRow(i);
			}
				prPanel.setVisible(true);
			}
		});
		mainPanel.add(btnPRButton);

		lblNhom = new JLabel("\u00A9 Nh\u00F3m 5 - KSTN CNTT K58");
		lblNhom.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mainPanel.add(lblNhom);

		lblSponsor = new JLabel("Designed By Chó To Xấu Tính");
		lblSponsor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSponsor.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(lblSponsor);

		lblBackground = new JLabel(""){
			private BufferedImage originalImage = ImageIO.read(TreeApp.class.getResource("/tree/chotoxautinh.jpg"));
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
			}
		};
		//		lblNewLabel.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/chotoxautinh.jpg")));
		lblBackground.setBounds(0, 0, 684, 440);
		mainPanel.add(lblBackground);

		kdPanel = new KDTreeView(kdTree);
		layout.add(kdPanel, "name_47820586107219");
		prevKdPanel = new JPanel();

		kdPanel.add(prevKdPanel, BorderLayout.NORTH);
		prevKdPanel.setLayout(new BorderLayout(0, 0));

		btnKdPrevButton = new JButton("Prev");
		btnKdPrevButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnKdPrevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				kdPanel.listModel.clear();
				kdTree.clear();
				kdPanel.setVisible(false);
			}
		});
		btnKdPrevButton.setVerticalAlignment(SwingConstants.TOP);
		btnKdPrevButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/arrow-left-128.png")));
		prevKdPanel.add(btnKdPrevButton, BorderLayout.WEST);

		avlPanel = new AVLTreeView(avlTree);
		layout.add(avlPanel, "name_191048221419144");

		prevAvlPanel = new JPanel();
		avlPanel.add(prevAvlPanel, BorderLayout.NORTH);
		prevAvlPanel.setLayout(new BorderLayout(0, 0));

		btnAvlPrevButton = new JButton("Prev");
		btnAvlPrevButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnAvlPrevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				avlTree.makeEmpty();
				avlPanel.setVisible(false);
			}
		});
		btnAvlPrevButton.setVerticalAlignment(SwingConstants.TOP);
		btnAvlPrevButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/arrow-left-128.png")));
		prevAvlPanel.add(btnAvlPrevButton, BorderLayout.WEST);

		triesPanel = new TriesView(huffman);
		layout.add(triesPanel, "name_191048256707292");

		prevTriesPanel = new JPanel();
		triesPanel.add(prevTriesPanel, BorderLayout.NORTH);
		prevTriesPanel.setLayout(new BorderLayout(0, 0));
		btnTriesPrevButton = new JButton("Prev");
		btnTriesPrevButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnTriesPrevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				huffmanPanel.setVisible(true);
				triesPanel.setVisible(false);
			}
		});
		btnTriesPrevButton.setVerticalAlignment(SwingConstants.TOP);
		btnTriesPrevButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/arrow-left-128.png")));
		prevTriesPanel.add(btnTriesPrevButton, BorderLayout.WEST);

		huffmanPanel = new JPanel();
		layout.add(huffmanPanel, "name_191048292234512");
		huffmanPanel.setLayout(new BorderLayout(0, 0));

		prevHuffmanPanel = new JPanel();
		huffmanPanel.add(prevHuffmanPanel, BorderLayout.NORTH);
		GridBagLayout gbl_prevHuffmanPanel = new GridBagLayout();
		gbl_prevHuffmanPanel.columnWidths = new int[]{248, 187, 0};
		gbl_prevHuffmanPanel.rowHeights = new int[]{137, 0};
		gbl_prevHuffmanPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_prevHuffmanPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		prevHuffmanPanel.setLayout(gbl_prevHuffmanPanel);

		btnHuffmanPrevButton = new JButton("Prev");
		btnHuffmanPrevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				huffmanPanel.setVisible(false);
				mainPanel.setVisible(true);
			}
		});
		btnHuffmanPrevButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnHuffmanPrevButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/arrow-left-128.png")));
		GridBagConstraints gbc_btnHuffmanPrevButton = new GridBagConstraints();
		gbc_btnHuffmanPrevButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnHuffmanPrevButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnHuffmanPrevButton.gridx = 0;
		gbc_btnHuffmanPrevButton.gridy = 0;
		prevHuffmanPanel.add(btnHuffmanPrevButton, gbc_btnHuffmanPrevButton);

		huffmanBodyPanel = new JPanel();
		huffmanPanel.add(huffmanBodyPanel);
		huffmanBodyPanel.setLayout(null);

		introPanel = new JPanel();
		introPanel.setBorder(null);
		introPanel.setBounds(343, 11, 284, 253);
		huffmanBodyPanel.add(introPanel);
		GridBagLayout gbl_introPanel = new GridBagLayout();
		gbl_introPanel.columnWidths = new int[]{0, 0};
		gbl_introPanel.rowHeights = new int[]{0, 0, 0};
		gbl_introPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_introPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		introPanel.setLayout(gbl_introPanel);

		txtpnNhpVoGi = new JTextPane();
		txtpnNhpVoGi.setContentType("text/html");
		txtpnNhpVoGi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpnNhpVoGi.setEditable(false);
		txtpnNhpVoGi.setBackground(UIManager.getColor("Button.background"));
		txtpnNhpVoGi.setText("<p style=\"text-align:justify; font-size: 13px; margin: 15px\">Nh\u1EADp v\u00E0o gi\u00E1 tr\u1ECB t\u1EA7n su\u1EA5t c\u1EE7a c\u00E1c k\u00FD t\u1EF1 r\u1ED3i b\u1EA5m v\u00E0o n\u00FAt Generate \u0111\u1EC3 t\u1EA1o 1 Tries bi\u1EC3u di\u1EC5n m\u00E3 h\u00F3a Huffman</p>");
		GridBagConstraints gbc_txtpnNhpVoGi = new GridBagConstraints();
		gbc_txtpnNhpVoGi.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnNhpVoGi.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtpnNhpVoGi.gridx = 0;
		gbc_txtpnNhpVoGi.gridy = 0;
		introPanel.add(txtpnNhpVoGi, gbc_txtpnNhpVoGi);
		btnGenerateButton = new JButton("Generate");
		btnGenerateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean checkSynctax = true;
				int[] freAry = new int[7];
				for (int i = 0; i < txtFieldAry.size(); i++) {
					String txt = txtFieldAry.get(i).getText();
					if (txt.matches("^\\d+$") == false) {
						checkSynctax = false;
						break;
					}
					freAry[i] = Integer.valueOf(txt);
				}
				if (checkSynctax == false) {
					JOptionPane.showMessageDialog(null, "Giá trị các text field phải là số nguyên dương", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} else {
					huffmanPanel.setVisible(false);
					char[] charAry = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

					huffman.buildTree(freAry, charAry);
					triesPanel.curNode = triesPanel.tree.root;
					triesPanel.paintTree.setPreferredSize(new Dimension((int) Math.pow(2, (1 + triesPanel.tree.getDepth())) * triesPanel.paintTree.getRadius(), (1 + triesPanel.tree.getDepth()) * triesPanel.paintTree.getVGap() + triesPanel.paintTree.getRadius()));
					triesPanel.scrollPane.setPreferredSize(new Dimension((int) Math.pow(2, triesPanel.tree.getDepth() + 1) * triesPanel.paintTree.getRadius(), (1 + triesPanel.tree.getDepth()) * triesPanel.paintTree.getVGap() + triesPanel.paintTree.getRadius()));
					triesPanel.paintTree.repaint(); // Redisplay the tree
					triesPanel.scrollPane.updateUI(); // Update scroll bar
					triesPanel.setVisible(true);
				}
			}
		});
		GridBagConstraints gbc_btnGenerateButton = new GridBagConstraints();
		gbc_btnGenerateButton.gridx = 0;
		gbc_btnGenerateButton.gridy = 1;
		introPanel.add(btnGenerateButton, gbc_btnGenerateButton);

		frequencyForm = new JPanel();
		frequencyForm.setBorder(null);
		frequencyForm.setBounds(56, 11, 235, 253);
		huffmanBodyPanel.add(frequencyForm);
		GridBagLayout gbl_frequencyForm = new GridBagLayout();
		gbl_frequencyForm.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_frequencyForm.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_frequencyForm.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_frequencyForm.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frequencyForm.setLayout(gbl_frequencyForm);

		lblA = new JLabel("A:");
		lblA.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 1;
		gbc_lblA.gridy = 1;
		frequencyForm.add(lblA, gbc_lblA);

		aTextField = new JTextField();
		GridBagConstraints gbc_aTextField = new GridBagConstraints();
		gbc_aTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_aTextField.insets = new Insets(0, 0, 5, 0);
		gbc_aTextField.gridx = 3;
		gbc_aTextField.gridy = 1;
		frequencyForm.add(aTextField, gbc_aTextField);
		aTextField.setColumns(10);
		txtFieldAry.add(aTextField);

		lblB = new JLabel("B:");
		lblB.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblB = new GridBagConstraints();
		gbc_lblB.insets = new Insets(0, 0, 5, 5);
		gbc_lblB.gridx = 1;
		gbc_lblB.gridy = 2;
		frequencyForm.add(lblB, gbc_lblB);

		bTextField = new JTextField();
		bTextField.setColumns(10);
		GridBagConstraints gbc_bTextField = new GridBagConstraints();
		gbc_bTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_bTextField.insets = new Insets(0, 0, 5, 0);
		gbc_bTextField.gridx = 3;
		gbc_bTextField.gridy = 2;
		frequencyForm.add(bTextField, gbc_bTextField);
		txtFieldAry.add(bTextField);

		lblC = new JLabel("C:");
		lblC.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.insets = new Insets(0, 0, 5, 5);
		gbc_lblC.gridx = 1;
		gbc_lblC.gridy = 3;
		frequencyForm.add(lblC, gbc_lblC);

		cTextField = new JTextField();
		cTextField.setColumns(10);
		GridBagConstraints gbc_cTextField = new GridBagConstraints();
		gbc_cTextField.insets = new Insets(0, 0, 5, 0);
		gbc_cTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cTextField.gridx = 3;
		gbc_cTextField.gridy = 3;
		frequencyForm.add(cTextField, gbc_cTextField);
		txtFieldAry.add(cTextField);

		lblD = new JLabel("D:");
		lblD.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblD = new GridBagConstraints();
		gbc_lblD.insets = new Insets(0, 0, 5, 5);
		gbc_lblD.gridx = 1;
		gbc_lblD.gridy = 4;
		frequencyForm.add(lblD, gbc_lblD);

		dTextField = new JTextField();
		dTextField.setColumns(10);
		GridBagConstraints gbc_dTextField = new GridBagConstraints();
		gbc_dTextField.insets = new Insets(0, 0, 5, 0);
		gbc_dTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dTextField.gridx = 3;
		gbc_dTextField.gridy = 4;
		frequencyForm.add(dTextField, gbc_dTextField);
		txtFieldAry.add(dTextField);

		lblE = new JLabel("E:");
		lblE.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblE = new GridBagConstraints();
		gbc_lblE.insets = new Insets(0, 0, 5, 5);
		gbc_lblE.gridx = 1;
		gbc_lblE.gridy = 5;
		frequencyForm.add(lblE, gbc_lblE);

		eTextField = new JTextField();
		eTextField.setColumns(10);
		GridBagConstraints gbc_eTextField = new GridBagConstraints();
		gbc_eTextField.insets = new Insets(0, 0, 5, 0);
		gbc_eTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_eTextField.gridx = 3;
		gbc_eTextField.gridy = 5;
		frequencyForm.add(eTextField, gbc_eTextField);
		txtFieldAry.add(eTextField);

		lblF = new JLabel("F:");
		lblF.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblF = new GridBagConstraints();
		gbc_lblF.insets = new Insets(0, 0, 5, 5);
		gbc_lblF.gridx = 1;
		gbc_lblF.gridy = 6;
		frequencyForm.add(lblF, gbc_lblF);

		fTextField = new JTextField();
		fTextField.setColumns(10);
		GridBagConstraints gbc_fTextField = new GridBagConstraints();
		gbc_fTextField.insets = new Insets(0, 0, 5, 0);
		gbc_fTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fTextField.gridx = 3;
		gbc_fTextField.gridy = 6;
		frequencyForm.add(fTextField, gbc_fTextField);
		txtFieldAry.add(fTextField);

		lblG = new JLabel("G:");
		lblG.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblG = new GridBagConstraints();
		gbc_lblG.insets = new Insets(0, 0, 5, 5);
		gbc_lblG.gridx = 1;
		gbc_lblG.gridy = 7;
		frequencyForm.add(lblG, gbc_lblG);

		gTextField = new JTextField();
		gTextField.setColumns(10);
		GridBagConstraints gbc_gTextField = new GridBagConstraints();
		gbc_gTextField.insets = new Insets(0, 0, 5, 0);
		gbc_gTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_gTextField.gridx = 3;
		gbc_gTextField.gridy = 7;
		frequencyForm.add(gTextField, gbc_gTextField);
		txtFieldAry.add(gTextField);

		splayPanel = new SplayTreeView(splayTree);
		layout.add(splayPanel, "name_7479767404581");

		prevSplayPanel = new JPanel();
		splayPanel.add(prevSplayPanel, BorderLayout.NORTH);
		prevSplayPanel.setLayout(new BorderLayout(0, 0));

		btnSplayPrevButton = new JButton("Prev");
		btnSplayPrevButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnSplayPrevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				splayTree.clear();
				splayPanel.setVisible(false);
			}
		});
		btnSplayPrevButton.setVerticalAlignment(SwingConstants.TOP);
		btnSplayPrevButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/arrow-left-128.png")));
		prevSplayPanel.add(btnSplayPrevButton, BorderLayout.WEST);

		prPanel = new PrQuadTreeView(prTree);
		layout.add(prPanel, "name_17468345984423");

		prevPrPanel = new JPanel();
		prPanel.add(prevPrPanel, BorderLayout.NORTH);
		prevPrPanel.setLayout(new BorderLayout(0, 0));

		btnPrPrevButton = new JButton("Prev");
		btnPrPrevButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnPrPrevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				prPanel.listModel.clear();
				prTree.clear();
				prPanel.setVisible(false);
			}
		});
		btnPrPrevButton.setVerticalAlignment(SwingConstants.TOP);
		btnPrPrevButton.setIcon(new ImageIcon(TreeApp.class.getResource("/tree/arrow-left-128.png")));
		prevPrPanel.add(btnPrPrevButton, BorderLayout.WEST);

		prPanel.setVisible(false);
		kdPanel.setVisible(false);
		splayPanel.setVisible(false);
		huffmanPanel.setVisible(false);
		triesPanel.setVisible(false);
		avlPanel.setVisible(false);

		JMenuBar menuBar = new JMenuBar();
		frmAdvancedTree.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnFile.add(mntmExit);

		JMenu mnNewMenu = new JMenu("About");
		menuBar.add(mnNewMenu);

		JMenuItem mntmAbout = new JMenuItem("Author");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, " Phan Đức Việt\n Trương Giang Khang\n Vũ Văn Chung\n Trần Mộng Long", "Thành Viên",  JOptionPane.PLAIN_MESSAGE);
			}
		});
		mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnNewMenu.add(mntmAbout);

	}
}
