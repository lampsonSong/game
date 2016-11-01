package com.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MainFrame extends JFrame {

	public static final int FRAME_WIDTH = 900;
	public static final int FRAME_HEIGHT = 700;
	public static final String URL = "images/";
	public static final Point POINT = new Point(10,600);
	public static String ipAddress = null;
	public static String port = null;
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JLabel jLabel3;
	private JTextField jTextField0;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	
	private CardGroup cg1 = null;
	private CardGroup cTemp = null;
	private CardGroup cgleft = null;
	private CardGroup cgright = null;
	private CardGroup cgcenter = null;
	private CardGroup cgabove = null;
	private CardGroup cgextra = null;
	private CardGroup cOpponent[] = new CardGroup[3]; 
	private int rowsnum = 0;
	private int leftNum = 17;
	private int rightNum = 17;
	private int opponentNum = 17;
	private int robtimes = 0;
	private boolean isFirstRound = true;
	private boolean isConnOver = false;
	private boolean sign = false;
	private char robchance = 'N';
	private Socket s = null;
	public static int turnFlag = 0;
	private int whoseturn = 0;
	private int whoseFlag = -1;
	Timer timer=new  Timer(1000, new  actionl1());
	Timer timersee=new  Timer(1000, new  actionl2());
	Timer timerlook=new  Timer(1000, new  actionl3());
	private int i = 30;
	private int right = 30;
	private int left = 30;
	
	private JButton jButton5;
	private JButton jButton6;
	private JTextArea jTextArea0;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JButton jButton7;
	private JButton jButton8;
	private JButton jButton9;
	private JButton jButton11;
	private JButton jButton12;
	private JButton jButton10;
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JTextField jTextField2;
	private JTextField jTextField3;
	private JTextField jTextField1;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	
	
	
	public MainFrame() {
		
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		initComponents();
		new Thread(new PaintThread()).start();
	}

	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GroupLayout());
		add(getJPanel1(), new Constraints(new Leading(0, 889, 12, 12), new Bilateral(0, 0, 0)));
		add(getJPanel2(), new Constraints(new Leading(895, 222, 10, 10), new Leading(4, 692, 10, 10)));
		add(getJLabel3(), new Constraints(new Leading(0, 1120, 12, 12), new Leading(0, 596, 596)));
		setSize(1120, 700);
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
			jTextField1.setText("30");
			jTextField1.setVisible(false);
			jTextField1.setOpaque(false);
			jTextField1.setBorder(null);
		}
		return jTextField1;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setHorizontalAlignment(SwingConstants.CENTER);
			jTextField3.setText("30");
			jTextField3.setVisible(false);
			jTextField3.setOpaque(false);
			jTextField3.setBorder(null);
		}
		return jTextField3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setEditable(false);
			jTextField2.setHorizontalAlignment(SwingConstants.CENTER);
			jTextField2.setText("30");
			jTextField2.setVisible(false);
			jTextField2.setOpaque(false);
			jTextField2.setBorder(null);
		}
		return jTextField2;
	}

	
		
	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setIcon(new ImageIcon(getClass().getResource("/others/004.png")));
			jLabel8.setVisible(false);
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setIcon(new ImageIcon(getClass().getResource("/others/004.png")));
			jLabel7.setVisible(false);
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setIcon(new ImageIcon(getClass().getResource("/others/004.png")));
			jLabel6.setVisible(false);
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setIcon(new ImageIcon(getClass().getResource("/others/pass.gif")));
			jLabel5.setVisible(false);
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setIcon(new ImageIcon(getClass().getResource("/others/pass.gif")));
			jLabel4.setVisible(false);
		}
		return jLabel4;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setIcon(new ImageIcon(getClass().getResource("/others/pass.gif")));
			jLabel2.setVisible(false);
		}
		return jLabel2;
	}

	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
			jButton10.setIcon(new ImageIcon(getClass().getResource("/others/009.png")));
			jButton10.setVisible(false);
			jButton10.setBorderPainted(false);
			jButton10.setOpaque(false);
			jButton10.setBorder(null);
			jButton10.setContentAreaFilled(false);
		}
		return jButton10;
	}

	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setIcon(new ImageIcon(getClass().getResource("/others/009.png")));
			jButton12.setVisible(false);
			jButton12.setBorderPainted(false);
			jButton12.setOpaque(false);
			jButton12.setBorder(null);
			jButton12.setContentAreaFilled(false);
		}
		return jButton12;
	}

	private JButton getJButton11() {                         //抢地主
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setIcon(new ImageIcon(getClass().getResource("/others/009.png")));
			jButton11.setVisible(false);
			jButton11.setBorderPainted(false);
			jButton11.setOpaque(false);
			jButton11.setBorder(null);
			jButton11.setContentAreaFilled(false);
		}
		return jButton11;
	}

	private JButton getJButton9() {							//左边地主图标
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setIcon(new ImageIcon(getClass().getResource("/others/010.png")));
			jButton9.setVisible(false);
			jButton9.setBorderPainted(false);
			jButton9.setOpaque(false);
			jButton9.setBorder(null);
			jButton9.setContentAreaFilled(false);
		}
		return jButton9;
	}

	private JButton getJButton8() {							//右边地主图标
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setIcon(new ImageIcon(getClass().getResource("/others/010.png")));
			jButton8.setVisible(false);
			jButton8.setBorderPainted(false);
			jButton8.setOpaque(false);
			jButton8.setBorder(null);
			jButton8.setContentAreaFilled(false);
		}
		return jButton8;
	}

	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setIcon(new ImageIcon(getClass().getResource("/others/010.png")));
			jButton7.setVisible(false);
			jButton7.setBorderPainted(false);
			jButton7.setOpaque(false);
			jButton7.setBorder(null);
			jButton7.setContentAreaFilled(false);
		}
		return jButton7;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setForeground(Color.white);
			jLabel1.setText("余牌: 17张");
			jLabel1.setVisible(false);
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setForeground(Color.white);
			jLabel0.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel0.setText("余牌:17张");
			jLabel0.setVisible(false);
		}
		return jLabel0;
	}

	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
			jTextArea0.setEditable(false);
			jTextArea0.setFont(new Font("楷体", Font.BOLD, 16));
			jTextArea0.setForeground(Color.yellow);
			jTextArea0.setLineWrap(true);
			jTextArea0.setRows(34);
			jTextArea0.setSize(220, 690);
			jTextArea0.setWrapStyleWord(true);
			jTextArea0.setOpaque(false);
			jTextArea0.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		}
		return jTextArea0;
	}

	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setIcon(new ImageIcon(getClass().getResource("/others/013.png")));
			jButton6.setVisible(false);
			jButton6.setBorderPainted(false);
			jButton6.setOpaque(false);
			jButton6.setPressedIcon(new ImageIcon(getClass().getResource("/others/0130.png")));
			jButton6.setBorder(null);
			jButton6.setContentAreaFilled(false);
			jButton6.setRolloverIcon(new ImageIcon(getClass().getResource("/others/013.png")));
		}
		return jButton6;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setIcon(new ImageIcon(getClass().getResource("/others/012.png")));
			jButton5.setVisible(false);
			jButton5.setBorderPainted(false);
			jButton5.setOpaque(false);
			jButton5.setPressedIcon(new ImageIcon(getClass().getResource("/others/0120.png")));
			jButton5.setBorder(null);
			jButton5.setContentAreaFilled(false);
			jButton5.setRolloverIcon(new ImageIcon(getClass().getResource("/others/012.png")));
			jButton5.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton5MouseMouseClicked(event);
				}
			});
		}
		return jButton5;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setIcon(new ImageIcon(getClass().getResource("/others/006.png")));
			jButton4.setVisible(false);
			jButton4.setBorderPainted(false);
			jButton4.setOpaque(false);
			jButton4.setPressedIcon(new ImageIcon(getClass().getResource("/others/0060.png")));
			jButton4.setContentAreaFilled(false);
			jButton4.setRolloverIcon(new ImageIcon(getClass().getResource("/others/006.png")));
			jButton4.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton4MouseMouseClicked(event);
				}
			});
		}
		return jButton4;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setIcon(new ImageIcon(getClass().getResource("/others/005.png")));
			jButton3.setVisible(false);
			jButton3.setBorderPainted(false);
			jButton3.setOpaque(false);
			jButton3.setPressedIcon(new ImageIcon(getClass().getResource("/others/0050.png")));
			jButton3.setBorder(null);
			jButton3.setContentAreaFilled(false);
			jButton3.setRolloverIcon(new ImageIcon(getClass().getResource("/others/005.png")));
			jButton3.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton3MouseMouseClicked(event);
				}
			});
		}
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setIcon(new ImageIcon(getClass().getResource("/others/003.png")));
			jButton2.setVisible(false);
			jButton2.setBorderPainted(false);
			jButton2.setOpaque(false);
			jButton2.setPressedIcon(new ImageIcon(getClass().getResource("/others/0030.png")));
			jButton2.setBorder(null);
			jButton2.setContentAreaFilled(false);
			jButton2.setRolloverIcon(new ImageIcon(getClass().getResource("/others/003.png")));
			jButton2.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton2MouseMouseClicked(event);
				}
			});
		}
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setIcon(new ImageIcon(getClass().getResource("/others/001.png")));
			jButton1.setVisible(false);
			jButton1.setBorderPainted(false);
			jButton1.setOpaque(false);
			jButton1.setPressedIcon(new ImageIcon(getClass().getResource("/others/0010.png")));
			jButton1.setBorder(null);
			jButton1.setContentAreaFilled(false);
			jButton1.setRolloverIcon(new ImageIcon(getClass().getResource("/others/001.png")));
			jButton1.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton1MouseMouseClicked(event);
				}
			});
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setIcon(new ImageIcon(getClass().getResource("/others/011.png")));
			jButton0.setBorderPainted(false);
			jButton0.setOpaque(false);
			jButton0.setPressedIcon(new ImageIcon(getClass().getResource("/others/0110.png")));
			jButton0.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
			jButton0.setContentAreaFilled(false);
			jButton0.setRolloverIcon(new ImageIcon(getClass().getResource("/others/011.png")));
			jButton0.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("")));
			jButton0.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton0MouseMouseClicked(event);
				}
			});
		}
		return jButton0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setEditable(false);
			jTextField0.setFont(new Font("楷体", Font.BOLD, 18));
			jTextField0.setForeground(new Color(255, 128, 64));
			jTextField0.setHorizontalAlignment(SwingConstants.LEFT);
			jTextField0.setCaretColor(Color.black);
			jTextField0.setOpaque(false);
			jTextField0.setBorder(BorderFactory.createEmptyBorder(2, 3, 0, 0));
			jTextField0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jTextField0ActionActionPerformed(event);
				}
			});
		}
		return jTextField0;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setIcon(new ImageIcon(getClass().getResource("/others/background.jpg")));
			jLabel3.setText("jLabel3");
		}
		return jLabel3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setOpaque(false);
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJTextField0(), BorderLayout.SOUTH);
			jPanel2.add(getJTextArea0(), BorderLayout.NORTH);
		}
		return jPanel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setForeground(Color.green);
			jPanel1.setFocusTraversalPolicyProvider(true);
			jPanel1.setOpaque(false);
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton2(), new Constraints(new Leading(502, 75, 10, 10), new Leading(505, 27, 10, 10)));
			jPanel1.add(getJButton1(), new Constraints(new Leading(319, 74, 10, 10), new Leading(507, 25, 12, 12)));
			jPanel1.add(getJButton5(), new Constraints(new Leading(777, 10, 10), new Leading(544, 12, 12)));
			jPanel1.add(getJButton6(), new Constraints(new Leading(777, 12, 12), new Leading(589, 10, 10)));
			jPanel1.add(getJLabel0(), new Constraints(new Leading(95, 10, 10), new Leading(504, 12, 12)));
			jPanel1.add(getJLabel1(), new Constraints(new Leading(747, 10, 10), new Leading(504, 12, 12)));
			jPanel1.add(getJButton0(), new Constraints(new Leading(407, 88, 10, 10), new Leading(363, 31, 10, 10)));
			jPanel1.add(getJButton4(), new Constraints(new Leading(506, 74, 10, 10), new Leading(504, 28, 12, 12)));
			jPanel1.add(getJButton3(), new Constraints(new Leading(316, 80, 12, 12), new Leading(504, 28, 12, 12)));
			jPanel1.add(getJButton8(), new Constraints(new Trailing(12, 12, 12), new Leading(240, 10, 10)));
			jPanel1.add(getJButton9(), new Constraints(new Leading(14, 10, 10), new Leading(240, 12, 12)));
			jPanel1.add(getJButton12(), new Constraints(new Trailing(12, 93, 93), new Leading(251, 10, 10)));
			jPanel1.add(getJButton11(), new Constraints(new Leading(12, 91, 91), new Leading(251, 12, 12)));
			jPanel1.add(getJButton7(), new Constraints(new Leading(64, 10, 10), new Leading(531, 12, 12)));
			jPanel1.add(getJButton10(), new Constraints(new Leading(59, 10, 10), new Leading(538, 12, 12)));
			jPanel1.add(getJLabel2(), new Constraints(new Leading(416, 12, 12), new Leading(430, 12, 12)));
			jPanel1.add(getJLabel4(), new Constraints(new Leading(205, 10, 10), new Leading(240, 10, 10)));
			jPanel1.add(getJLabel5(), new Constraints(new Leading(635, 10, 10), new Leading(240, 12, 12)));
			jPanel1.add(getJTextField1(), new Constraints(new Leading(438, 23, 12, 12), new Leading(492, 10, 10)));
			jPanel1.add(getJLabel6(), new Constraints(new Leading(420, 12, 12), new Leading(468, 12, 12)));
			jPanel1.add(getJTextField2(), new Constraints(new Leading(658, 34, 12, 12), new Leading(307, 12, 12)));
			jPanel1.add(getJLabel7(), new Constraints(new Leading(644, 10, 10), new Leading(282, 10, 10)));
			jPanel1.add(getJTextField3(), new Constraints(new Leading(173, 43, 10, 10), new Leading(299, 12, 12)));
			jPanel1.add(getJLabel8(), new Constraints(new Leading(165, 97, 97), new Leading(276, 10, 10)));
		}
		return jPanel1;
	}

	@SuppressWarnings("unused")
	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
				frame.setTitle("MainFrame");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
	
	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//System.out.println("This PaintThread is triggered!");
			}
		}
	}
	
	public void connServer(){
		try {
			s = new Socket(ipAddress, Integer.parseInt(port));
			jButton0.setVisible(false);
			jTextArea0.setText("系统提示：成功连接服务器\n\n");		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(s == null){
			System.out.println("Can not set up the network connection.");
			System.exit(-1);
		}
			
		(new Thread(new ReceiveThread())).start();
		
	}
	
	private void drawCards(CardGroup cg){
		if(cg == null) return;
		Iterator<Card> it = cg.getCards().descendingIterator();
		while(it.hasNext()){
			Card c = it.next();
			jPanel1.add(c,new Constraints(new Leading(c.getLocation().x, 10, 10), new Leading(c.getLocation().y, 10, 10)));
		}

	}
	
	class ReceiveThread implements Runnable
	{
		
		public void run()
		{
			if(isConnOver == false)										
			{
				if(s == null) return;
				try {
					DataInputStream dis = new DataInputStream(s.getInputStream());
					String str1 = dis.readUTF();
					turnFlag = str1.charAt(str1.length()-1)-48;
					String str = str1.substring(0, str1.length()-1);	
					while (str != null && str.length() != 0)
					{
						if(whoseturn == 1 || whoseturn ==2)
							isFirstRound = true;
						Point p = new Point(0,560);
						if(str.charAt(0) == 'B' || str.charAt(0) == 'O' || str.charAt(0) == 'R' || str.charAt(0) == 'M')
							updateFrame(str);
						if(str.charAt(0) == 'S' && str.charAt(1) != 'S')
						{
							updateAll();
							send("SS:" + Integer.toString(turnFlag));
							JudgeRoundOver(str);
						}
						if(str.charAt(0)== 'Y' || str.charAt(0) == 'D' || str.charAt(0) == 'T' || str.charAt(0) == 'P')
						{							
							displayReceiveCards(str);
						}
						if(str.charAt(0) == 'E')
						{
							displayInitCards(str,p);
						}	
						str = dis.readUTF();
					}
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void send(String str)
	{
		try {
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// B--B:X:X	kai shi qiang	O--O:X	order	M--M:X:xxxxxxx	message		R--R:X  rob
	public void updateFrame(String str)
	{
		if(str.charAt(0) == 'R' || str.charAt(0) == 'B')
		{
			rob(str);
		}
		if(str.charAt(0) == 'O' && str.charAt(2) == '1')
		{
			cgright.uncoverCards();
			jLabel1.setVisible(true);
			jTextField0.setEditable(true);
			jTextArea0.append("系统提示：玩家2加入游戏\n");
		}
		if(str.charAt(0) == 'B' && str.charAt(4) == '2')
		{
			cgleft.uncoverCards();
			cgcenter.uncoverCards();
			jLabel0.setVisible(true);
			if(turnFlag != 2)
				jTextArea0.append("系统提示：玩家3加入游戏\n");
		}
		if(str.charAt(0) == 'M')
		{
			if(rowsnum == 31)
			{
				jTextArea0.setText("");
				rowsnum = 0;
			}
			if(Integer.parseInt(str.substring(2,3)) == turnFlag)
			{
				String temp = gainSystemTime();
				jTextArea0.append("自己" + "[" + temp + "]:" + str.substring(4) + "\n");
				rowsnum++;
			}
			else
			{
				String temp = gainSystemTime();
				jTextArea0.append("玩家" + (Integer.parseInt(str.substring(2,3))+1)  + "[" + temp + "]:" + str.substring(4) + "\n");
				rowsnum++;
			}
		}
	}
	
	private String gainSystemTime(){
        Date now=new Date();
        Calendar cal=Calendar.getInstance();
        //DateFormat d=DateFormat.getDateTimeInstance();
        DateFormat d=DateFormat.getTimeInstance();
        String str=d.format(now);
		   return str;
	}

	public void displayInitCards(String str, Point position){
		String[] sub = null;
		int count = 0;
		int record = 0;
		Point p = new Point(position);
		StringBuffer strbuf = new StringBuffer(str);
		String strextra = new String();
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i) == 'I')
				record = i;
		}
		strextra = str.substring(0, record);
		dealcgextra(strextra);
		strbuf.delete(0, record);
		if(turnFlag > 0)
			jTextField0.setEditable(true);
		if (strbuf.charAt(0) == 'I') {
			strbuf.delete(0, 5);
			for(int i=0; i<strbuf.length(); i++)
			{
				if(strbuf.charAt(i) == ':')
					count++;							
			}
			sub = new String[count + 1];
			sub = strbuf.toString().split(":");
			if (cg1 != null) {
				deleteCardsGroup(cg1);
				cg1 = null;
			}
			cg1 = new CardGroup(sub, p);
			// 1.4 draw cg1 on the interface
			cg1.refreshCardsPositoin(0);
			cg1.unChosen();
			cgleft = new CardGroup(new Point(90,80));
			cgleft.hideCards();
			cgright = new CardGroup(new Point(740,80));
			cgright.hideCards();
			cgcenter = new CardGroup(80, new Point(FRAME_WIDTH/2-115, 15));
			cgcenter.hideCards();
			drawCards(cg1);
			drawCards(cgleft);
			drawCards(cgright);
			drawCards(cgcenter);
			jButton5.setVisible(true);
			jButton6.setVisible(true);
			if(turnFlag == 1)
			{
				cgright.uncoverCards();
				jLabel1.setVisible(true);
				send("O:" + Integer.toString(turnFlag));
			}
			if(turnFlag == 2)
			{
				double order = Math.random()*3;
				send("B" +  ":" + Integer.toString((int)(order)) + ":" + Integer.toString(turnFlag));
				jLabel0.setVisible(true);
				jLabel1.setVisible(true);
				cgright.uncoverCards();
				cgleft.uncoverCards();
				cgcenter.uncoverCards();
			}
		}
		
		else return;
	}
	
	public void	rob(String str)
	{
		int otherFlag1 = -1;
		int otherFlag2 = -1;
		if(str.charAt(0) == 'R')
		{
			otherFlag1 = Integer.parseInt(str.substring(2,3));	
			robchance = str.charAt(4);
			if(robchance == 'N')
				robtimes++;
		}
		if(str.charAt(0) == 'B')
			otherFlag2 = Integer.parseInt(str.substring(2,3));	//otherflag2是谁在抢地主
		if(robtimes != 3 )
		{
			if(robchance == 'Y')
			{
				cgabove.uncoverCards();
				cgcenter.hideCards();
				whoseFlag = otherFlag1;			// otherflag1谁是地主
				whoseturn = whoseFlag;
				jButton7.setVisible(false);
				jButton8.setVisible(false);
				jButton9.setVisible(false);
				jButton10.setVisible(true);
				
				
					if((turnFlag == 0 && otherFlag1 == 1) || (turnFlag == 1 && otherFlag1 == 2) || (turnFlag == 2 && otherFlag1 == 0))
					{
						jButton8.setVisible(true);
						jButton11.setVisible(true);
					}
					if((turnFlag == 0 && otherFlag1 == 2) || (turnFlag == 1 && otherFlag1 == 0) || (turnFlag == 2 && otherFlag1 == 1))
					{
						jButton9.setVisible(true);
						jButton12.setVisible(true);
					}
			}
			if(turnFlag == 0)
			{
				if(otherFlag2 == 0)
				{
					jButton1.setVisible(true);
					jButton2.setVisible(true);
					jButton7.setVisible(true);
				}
				else if(otherFlag2 == 1)
				{
					jButton8.setVisible(true);
				}
				else if(otherFlag2 == 2)
				{
					jButton9.setVisible(true);
				}
				else if(otherFlag1 == 1 && robchance == 'N')
				{
					jButton8.setVisible(false);
					jButton9.setVisible(true);
				}
				else if(otherFlag1 == 2 && robchance == 'N')
				{
					jButton9.setVisible(false);
					jButton1.setVisible(true);
					jButton2.setVisible(true);
					jButton7.setVisible(true);
				}
			}
			if(turnFlag == 1)
			{
				if(otherFlag2 == 0)
				{
					jButton9.setVisible(true);
				}
				else if(otherFlag2 == 1)
				{
					jButton1.setVisible(true);
					jButton2.setVisible(true);
					jButton7.setVisible(true);
				}
				else if(otherFlag2 == 2)
				{
					jButton8.setVisible(true);
				}
				else if(otherFlag1 == 0 && robchance == 'N')
				{
					jButton9.setVisible(false);
					jButton1.setVisible(true);
					jButton2.setVisible(true);
					jButton7.setVisible(true);
				}
				else if(otherFlag1 == 2 && robchance == 'N')
				{
					jButton8.setVisible(false);
					jButton9.setVisible(true);
				}
			}
			if(turnFlag == 2)
			{
				if(otherFlag2 == 0)
				{
					jButton8.setVisible(true);
				}
				else if(otherFlag2 == 1)
				{
					jButton9.setVisible(true);
				}
				else if(otherFlag2 == 2)
				{
					jButton1.setVisible(true);
					jButton2.setVisible(true);
					jButton7.setVisible(true);
				}
				else if(otherFlag1 == 0 && robchance == 'N')
				{
					jButton8.setVisible(false);
					jButton9.setVisible(true);
				}
				else if(otherFlag1 == 1 && robchance == 'N')
				{
					jButton9.setVisible(false);
					jButton1.setVisible(true);
					jButton2.setVisible(true);
					jButton7.setVisible(true);
				}
			}
		}
	}
	
	public void sendCards(CardGroup cg){ 
		StringBuffer sbuffOut = new StringBuffer();
		String strOut = new String();
		if(turnFlag == 0)
			sbuffOut.append("Y:");
		
		if(turnFlag == 1)
			sbuffOut.append("D:");
		if(turnFlag == 2)
			
			sbuffOut.append("T:");
		sbuffOut.append(cg.getCardsNumber());
		Iterator<Card> it = cg.getCards().iterator();
		while(it.hasNext())
		{
			Card card = it.next();
			sbuffOut.append(":");
			sbuffOut.append(card.getSuit());
			sbuffOut.append("-");
			if(card.getFigure() == 14)
				sbuffOut.append("1");
			else if(card.getFigure() == 16)
				sbuffOut.append("2");
			else sbuffOut.append(card.getFigure());
		}
		sbuffOut.append(turnFlag);
		strOut = sbuffOut.substring(0, sbuffOut.length());
		if((whoseturn == 0 || whoseturn == 1)&& isFirstRound == true)
		{
			whoseturn ++;
			isFirstRound = false;
		}
		if(whoseturn == 2 && isFirstRound == true)
			whoseturn =0;
		send(strOut);
	}
	
	
	/*
	private void getLeftTime1() {							//下面
		Timer timer=new  Timer(1000, new  actionl1());
		//jLabel6.setVisible(true);
		//jTextField1.setVisible(true);
		//jLabel2.setVisible(false);
		
		
		timer.start();
		
		}
	*/
	class  actionl1  implements  ActionListener {
		
		@Override
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jTextField1.setText(Integer.toString(i));
			
			i--;
			if(i == -1)
			{
				jTextField1.setText("0");		
				firepass();
				
				i = 30;
			}
		}
	}
	
	class actionl2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jTextField2.setText(Integer.toString(right));
			right -- ;
			if(right == -1)
			{
				jLabel7.setVisible(false);
				jTextField2.setVisible(false);
			}
		}
		
	}
	
	class actionl3 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jTextField3.setText(Integer.toString(left));
			left -- ;
			if(left == -1)
			{
				jLabel8.setVisible(false);
				jTextField3.setVisible(false);
			}
			
		}
		
	}
	/*
	private void getLeftTime2() {							//右边
		
		Timer timer=new  Timer(1000, new  actionl2());
		jLabel7.setVisible(true);
		jTextField2.setVisible(true);
		jLabel5.setVisible(false);
		timer.start();
		}
	
	class  actionl2  implements  ActionListener {
		int i = 10;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jTextField2.setText(Integer.toString(i));
			i--;
			if(i == -1)
			{
				jTextField2.setText("0");
				firepass();
				i = 10;
			}
		}
	}
	
	private void getLeftTime3() {							//左边
		Timer timer=new  Timer(1000, new  actionl3());
		//jLabel8.setVisible(true);
		//jTextField3.setVisible(true);
		jLabel4.setVisible(false);
		timer.start();
		}
	
	class  actionl3  implements  ActionListener {
		int i = 10;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jTextField3.setText(Integer.toString(i));
			i--;
			if(i == -1)
			{
				jTextField3.setText("0");
				firepass();
				i = 10;
			}
		}
	}
	*/
	
	
	
	
	public void displayReceiveCards(String str){
		if (str.charAt(0) != 'I' && str.charAt(0) != 'S') {
			
			if((whoseturn == 0 && turnFlag == 2)
					|| (whoseturn == 1 && turnFlag == 0)
					|| (whoseturn == 2 && turnFlag == 1))
			{
				if(turnFlag == 0 && cOpponent[2] != null)
				{
					deleteCardsGroup(cOpponent[2]);
					cOpponent[2] = null;
				}
					
				if(turnFlag == 1 && cOpponent[0] != null)
				{
					deleteCardsGroup(cOpponent[0]);
					cOpponent[0] = null;
				}
				if(turnFlag == 2 && cOpponent[1] != null)
				{
					deleteCardsGroup(cOpponent[0]);
					cOpponent[0] = null;
				}
				
				jLabel8.setVisible(true);
				jTextField3.setVisible(true);
				timerlook.restart();
				jLabel7.setVisible(false);
				jTextField2.setVisible(false);
				right = 30;
				//jLabel4.setVisible(false);
				
			}
			//whoseturn 刚刚谁出的牌
			if((whoseturn == 0 && turnFlag == 1) 			
				|| (whoseturn == 1 && turnFlag == 2)
				|| (whoseturn == 2 && turnFlag == 0))
			{
				
				
				jLabel2.setVisible(false);
				jLabel8.setVisible(false);
				jTextField3.setVisible(false);
				timerlook.stop();
				left = 30;
				
				if(turnFlag == 0 && cOpponent[2] != null)
				{
					deleteCardsGroup(cOpponent[2]);
					cOpponent[2] = null;
				}
					
				if(turnFlag == 1 && cOpponent[0] != null)
				{
					deleteCardsGroup(cOpponent[0]);
					cOpponent[0] = null;
				}
				if(turnFlag == 2 && cOpponent[1] != null)
				{
					deleteCardsGroup(cOpponent[0]);
					cOpponent[0] = null;
				}
				
				jLabel6.setVisible(true);
				jTextField1.setVisible(true);
				this.timer.start();
				
				
				
				if(cTemp != null)
				{
					deleteCardsGroup(cTemp);
					cTemp = null;
				}
				
			}
			
			//whoseturn变成下一家
			if((whoseturn == 0 || whoseturn == 1)&& isFirstRound == true)
			{
				whoseturn ++;
				isFirstRound = false;
			}
			if(whoseturn == 2 && isFirstRound == true)
				whoseturn =0;
			
			if(str.charAt(0) == 'P')
			{
				if(whoseturn == 0 && cOpponent[2] != null)
				{
					deleteCardsGroup(cOpponent[2]);
					cOpponent[2] = null;
				}
				if(whoseturn == 1 && cOpponent[0] != null)
				{
					deleteCardsGroup(cOpponent[0]);
					cOpponent[0] = null;
				}
				if(whoseturn == 2 && cOpponent[1] != null)
				{
					deleteCardsGroup(cOpponent[1]);
					cOpponent[1] = null;
				}
				if(cTemp != null)
				{
					cTemp.hideCards();
					cTemp = null;
				}
				int otherFlag = Integer.parseInt(str.substring(2,3));
				cOpponent[otherFlag] = null;						//otherFlag 出PASS的人
				if(turnFlag == 0)
				{
					if(otherFlag == 1)
						jLabel5.setVisible(true);
					if(otherFlag == 2)
					{	
						jLabel4.setVisible(true);
						jButton3.setVisible(true);
						jButton4.setVisible(true);
					}
				}
				if(turnFlag == 1)
				{
					if(otherFlag == 2)
					{
						jLabel5.setVisible(true);
					}
					if(otherFlag == 0)
					{
						jLabel4.setVisible(true);
						jButton3.setVisible(true);
						jButton4.setVisible(true);
					}
				}
				if(turnFlag == 2)
				{
					if(otherFlag == 0)
						jLabel5.setVisible(true);
					if(otherFlag == 1)
					{	
						jLabel4.setVisible(true);
						jButton3.setVisible(true);
						jButton4.setVisible(true);
					}
				}
			}
			else
			{
				if(turnFlag == whoseturn)
				{
					jButton3.setVisible(true);
					jButton4.setVisible(true);
					if(cTemp != null)
					{
						cTemp.hideCards();
					}
				}
				Point p10 = new Point(205,240);
				Point p20 = new Point(635,240);
				Point p01 = new Point(635,240);
				Point p21 = new Point(205,240);
				Point p02 = new Point(205,240);
				Point p12 = new Point(635,240);
				str = str.substring(0,str.length()-1);
				StringBuffer strbuf = new StringBuffer(str);
				int count = 0;
				String[] sub = null;
				for(int i = 0; i<str.length(); i++)						
				{
					if(str.charAt(i) == '-')
						count++;
				}
				if(count > 9)												
					strbuf.delete(0, 5);
				else
					strbuf.delete(0, 4);
				count = 0;
				for(int i=0; i<strbuf.length(); i++)
				{
					if(strbuf.charAt(i) == ':')
						count++;							
				}
				sub = new String[count + 1];
				sub = strbuf.toString().split(":");			
				
				if(str.charAt(0) == 'Y') // the fisrt client 从 0传过来的牌
				{
					if (cOpponent[0] != null) {
						deleteCardsGroup(cOpponent[0]);
						cOpponent[0] = null;
					}
					if(turnFlag == 1)
					{
						cOpponent[0] = new CardGroup(sub, p10);
						jLabel2.setVisible(false);
					}
					if(turnFlag == 2)
					{
						cOpponent[0] = new CardGroup(sub, p20);
						jLabel4.setVisible(false);
					}
					opponentNum = opponentNum - cOpponent[0].getCardsNumber();					
					
					// draw cOpponent on the interface
					//cOpponent[0].refreshCardsPositoin(0);
					drawCards(cOpponent[0]);	
				}
				
				if(str.charAt(0)== 'D' ) // the second client , 从 1传过来的牌
				{
					if(cOpponent[1] != null)
					{
						deleteCardsGroup(cOpponent[1]);
						cOpponent[1] = null;
					}
					if(turnFlag == 0)
					{
						cOpponent[1] = new CardGroup(sub,p01);
						jLabel4.setVisible(false);
					}
					if(turnFlag == 2)
					{
						cOpponent[1] = new CardGroup(sub,p21);
						jLabel2.setVisible(false);
					}
					//cOpponentLast.refreshCardsPositoin(10);
					drawCards(cOpponent[1]);
					
				}
				if(str.charAt(0) == 'T')		//the third client 从 2传过来的牌
				{
					if(cOpponent[2] != null)
					{
						deleteCardsGroup(cOpponent[2]);
						cOpponent[2] = null;
					}
					if(turnFlag == 0)
					{
						cOpponent[2] = new CardGroup(sub,p02);
						jLabel2.setVisible(false);
					}
					if(turnFlag == 1)
					{
						cOpponent[2] = new CardGroup(sub,p12);
						jLabel4.setVisible(false);
					}
					drawCards(cOpponent[2]);
					
				}
			}
		}
		else return;
	}
	
	public void deleteCardsGroup(CardGroup cg){
		if(cg.getCardsNumber() == 0){
			return;
		}
		Iterator<Card> it = cg.getCards().iterator();
		while(it.hasNext()){
			Card c = it.next();
			jPanel1.remove(c);
		}
	}
	
	public void JudgeRoundOver(String str){
		// if received a message from the server that start with "S", that means your opponent
		// has already played out all cards. Thus you lose this game.
		if(str.charAt(0) == 'S'){
			if(whoseFlag == Integer.parseInt(str.substring(2,3)))			//谁的地主 whoseflag
			{
				JOptionPane.showMessageDialog(null,"Please work hard and try it again!", "YOU LOSE",-1);
			}
			if(whoseFlag == turnFlag)
			{
				JOptionPane.showMessageDialog(null,"Please work hard and try it again!", "YOU LOSE",-1);
			}
			if(whoseFlag != Integer.parseInt(str.substring(2,3)) && whoseFlag != turnFlag)
				JOptionPane.showMessageDialog(null,"Please work hard and try it again!", "YOU WIN",-1);
			//new game start	
			updateAll();
			//drawCards(cg1);
		}
		else return;
	}
	
	private void dealcgextra(String str)
	{
		String[] sub = null;
		int count = 0;
		StringBuffer strbuf = new StringBuffer(str);
		strbuf.delete(0, 4);
		for(int i=0; i<strbuf.length(); i++)
		{
			if(strbuf.charAt(i) == ':')
				count++;							
		}
		sub = new String[count + 1];
		sub = strbuf.toString().split(":");
		cgextra = new CardGroup(sub,new Point());
		cgabove = new CardGroup(sub);
		cgabove.refreshCardsPositoin(0);
		drawCards(cgabove);
	}
	
	
	public void addextraCards(CardGroup cg)
	{
		for(int i = 0; i<3; i++)
		{
			cg.getCards().add(cgextra.getCards().get(i));
		}
		cg.setCardsNumber(cg.getCards().size());
		cg.refreshCardsPositoin(0);
		drawCards(cg);
		cgabove.uncoverCards();
		cgcenter.hideCards();
	}
	
	public void sendNewGameSignal(){
		// send "S:0" String signal to the server
		String strOut = new String("S:" + turnFlag);
		send(strOut);
	}
	
	private void updateAll()
	{
		if (cTemp != null) {
			deleteCardsGroup(cTemp);
			cTemp = null;
		}
		if (cg1 != null) {
			deleteCardsGroup(cg1);
			cg1 = null;
		}
		if (cOpponent[0] != null) {
			deleteCardsGroup(cOpponent[0]);
			cOpponent = null;
		}
		if (cOpponent[1] != null) {
			deleteCardsGroup(cOpponent[1]);
			cOpponent = null;
		}
		if (cOpponent[2] != null) {
			deleteCardsGroup(cOpponent[2]);
			cOpponent = null;
		}
		if(cgright != null)
		{
			deleteCardsGroup(cgright);
			cgright = null;
		}
		if(cgleft != null)
		{
			deleteCardsGroup(cgleft);
			cgleft = null;
		}
		if(cgcenter != null)
		{
			deleteCardsGroup(cgcenter);
			cgcenter = null;
		}
		if(cgextra != null)
		{
			deleteCardsGroup(cgextra);
			cgextra = null;
		}
		if(cgabove != null){
			deleteCardsGroup(cgabove);
			cgabove = null;
		}
		rowsnum = 0;
		leftNum = 17;
		rightNum = 17;
		opponentNum = 17;
		robtimes = 0;
		isFirstRound = true;
		isConnOver = false;
		robchance = 'N';
		whoseturn = 0;
		jButton0.setVisible(false);
		jButton1.setVisible(false);
		jButton2.setVisible(false);
		jButton3.setVisible(false);
		jButton4.setVisible(false);
		jButton5.setVisible(false);
		jButton6.setVisible(false);
		jButton7.setVisible(false);
		jButton8.setVisible(false);
		jButton9.setVisible(false);
		jButton10.setVisible(false);
		jButton11.setVisible(false);
		jButton12.setVisible(false);
		jLabel0.setText("余牌: 17张");
		jLabel0.setVisible(false);
		jLabel1.setText("余牌: 17张");
		jLabel1.setVisible(false);
		jLabel2.setVisible(false);
		jLabel4.setVisible(false);
		jLabel5.setVisible(false);
	}
	
	private void jButton0MouseMouseClicked(MouseEvent event) {
		NetWorkSetting nws = new NetWorkSetting();
		nws.setModal(true);
		nws.setVisible(true);
		System.out.println(ipAddress + "+" +port);
		isConnOver = false;
		connServer();
	}

	private void jButton5MouseMouseClicked(MouseEvent event) {
		cg1.sortByFigure();
		drawCards(cg1);
	}

	private void jButton1MouseMouseClicked(MouseEvent event) {
		send("R:" + Integer.toString(turnFlag) + ":Y");
		whoseturn = turnFlag;
		jButton1.setVisible(false);
		jButton2.setVisible(false);
		jButton7.setVisible(true);
		jButton11.setVisible(true);
		jButton12.setVisible(true);
		jButton8.setVisible(false);
		jButton9.setVisible(false);
		jButton3.setVisible(true);
		jButton4.setVisible(true);
		
		jLabel6.setVisible(true);
		jTextField1.setVisible(true);
		this.timer.restart();
		
		addextraCards(cg1);
	}

	private void jButton2MouseMouseClicked(MouseEvent event) {
		send("R:" + Integer.toString(turnFlag) + ":N");
		jButton1.setVisible(false);
		jButton2.setVisible(false);
		jButton7.setVisible(false);
		jButton8.setVisible(true);
		robtimes++;
		if(robtimes == 3)
		{
			sendNewGameSignal();
			robtimes = 0;
			updateAll();
		}
		else robtimes = 0;
	}

	private void jTextField0ActionActionPerformed(ActionEvent event) {
		String message = jTextField0.getText();
		if (message.trim().length() == 0)	return;
		send("M:"+ Integer.toString(turnFlag) + ":" + message);
		jTextField0.setText("");
	}

	private void jButton3MouseMouseClicked(MouseEvent event) {
		if(cTemp != null){
			cTemp.hideCards();
			cTemp = null;
		}
		if(turnFlag == whoseturn )
		{
			isFirstRound = true;	
			cTemp = cg1.chosenCards();
			//System.out.println("cTemp=" + cTemp);
			if(cTemp.getCards().size()>0){
			if(turnFlag == 0)
			{
				if(cOpponent[2] == null)
				{
					if(cOpponent[1] == null)
						sign = true;
					else
						sign = cTemp.biggerThan(cOpponent[1]);
				}
				else
					sign = cTemp.biggerThan(cOpponent[2]);
			}
			
			if(turnFlag == 1)
			{
				if(cOpponent[0] == null)
				{
					if(cOpponent[2] == null)
						sign = true;
					else
						sign = cTemp.biggerThan(cOpponent[2]);
				}
				else
					sign = cTemp.biggerThan(cOpponent[0]);
			}
			
			if(turnFlag == 2)
			{
				if(cOpponent[1] == null)
				{
					if(cOpponent[0] == null)
						sign = true;
					else
						sign = cTemp.biggerThan(cOpponent[0]);
				}
				else
					sign = cTemp.biggerThan(cOpponent[1]);
			}
			if(sign)
			{
				this.timer.stop();
				i = 30;
				jLabel6.setVisible(false);
				jTextField1.setVisible(false);
				this.timersee.stop();
				right = 30;
				jLabel7.setVisible(false);
				
				
				if(turnFlag == 0 && cOpponent[1] != null)
				{
					cOpponent[1].hideCards();
					
				}
				if(turnFlag == 1 && cOpponent[2] != null)
				{
					cOpponent[2].hideCards();
					
				}
				if(turnFlag == 2 && cOpponent[0] != null)
				{
					cOpponent[0].hideCards();
					
				}
				jLabel7.setVisible(true);
				jTextField2.setVisible(true);
				timersee.restart();
				
				jButton3.setVisible(false);
				jButton4.setVisible(false);
				jLabel2.setVisible(false);
				jLabel5.setVisible(false);
				cTemp.refreshCardsPositoin(130);
				sendCards(cTemp);
				drawCards(cTemp);
				cg1.deleteCards(cTemp);
				if(cg1.isFinished())
				{
					JOptionPane.showMessageDialog(null,"Congratulations to you for winning!" , "YOU WIN",-1);
					sendNewGameSignal();
					robtimes = 0;
					updateAll();
				}
				drawCards(cg1);
			}
			else
				cTemp = null;
			}
		}
	}

	private void jButton4MouseMouseClicked(MouseEvent event) {
		firepass();
	}
	
	private void firepass()
	{
		if(whoseturn == turnFlag)
		{
			
			this.timer.stop();
			i = 30;
			jLabel6.setVisible(false);
			jTextField1.setVisible(false);
			
			
			if(whoseturn == 1 || whoseturn == 2)
				isFirstRound = true;
			if(cTemp != null)
			{
				deleteCardsGroup(cTemp);
				cTemp = null;
			}
			if(turnFlag == 0 && cOpponent[1] != null)
			{
				cOpponent[1].hideCards();
			}
			if(turnFlag == 1 && cOpponent[2] != null)
			{
				cOpponent[2].hideCards();
			}
			if(turnFlag == 2 && cOpponent[0] != null)
			{
				cOpponent[0].hideCards();
			}
			jLabel7.setVisible(true);
			jTextField2.setVisible(true);
			timersee.restart();
			
			send("P:" + Integer.toString(turnFlag));
			if((whoseturn == 0 || whoseturn == 1)&& isFirstRound == true)
			{
				whoseturn ++;
				isFirstRound = false;
			}
			if(whoseturn == 2 && isFirstRound == true)
			{
				whoseturn =0;
			}
			jLabel2.setVisible(true);
			jButton3.setVisible(false);
			jButton4.setVisible(false);
			jLabel5.setVisible(false);
		}
	}
}
