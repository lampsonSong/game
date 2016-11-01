package com.cards;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

//VS4E -- DO NOT REMOVE THIS LINE!
public class NetWorkSetting extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JTextField jTextField0;
	private JTextField jTextField2;
	private JTextField jTextField1;
	private JTextField jTextField3;
	private JLabel jLabel1;
	private JTextField jTextField4;
	private JButton jButton0;
	private StringBuffer strbufip = new StringBuffer();
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public NetWorkSetting() {
		initComponents();
	}
	
	private void initComponents() {
		this.setLocation(500, 300);
		setTitle("NetWorking Setting");
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(33, 10, 10), new Leading(15, 10, 10)));
		add(getJTextField0(), new Constraints(new Leading(41, 44, 10, 10), new Leading(45, 10, 10)));
		add(getJTextField2(), new Constraints(new Leading(97, 44, 10, 10), new Leading(45, 10, 10)));
		add(getJTextField1(), new Constraints(new Leading(153, 44, 10, 10), new Leading(45, 10, 10)));
		add(getJTextField3(), new Constraints(new Leading(209, 44, 10, 10), new Leading(45, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(33, 12, 12), new Leading(80, 12, 12)));
		add(getJTextField4(), new Constraints(new Leading(41, 56, 12, 12), new Leading(105, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(115, 76, 10, 10), new Leading(135, 10, 10)));
		setSize(300, 200);
		setResizable(false);
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton0.setText("确定");
			jButton0.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton0MouseMouseClicked(event);
				}
			});
		}
		return jButton0;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.requestFocus();
			jTextField4.setText("8888");
		}
		return jTextField4;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel1.setText("端口号：");
		}
		return jLabel1;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setText("1");
		}
		return jTextField3;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setText("0");
		}
		return jTextField1;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setText("0");
		}
		return jTextField2;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setText("127");
		}
		return jTextField0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel0.setText("服务器IP：");
		}
		return jLabel0;
	}

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
				NetWorkSetting dialog = new NetWorkSetting();
				dialog.setDefaultCloseOperation(NetWorkSetting.DISPOSE_ON_CLOSE);
				dialog.setTitle("Test");
				dialog.setLocationRelativeTo(null);
				dialog.getContentPane().setPreferredSize(dialog.getSize());
				dialog.pack();
				dialog.setVisible(true);
			}
		});
	}

	private void jButton0MouseMouseClicked(MouseEvent event) {
		try{
			strbufip.append(jTextField0.getText());
			if(strbufip.toString().trim().length() == 0) return;
		}
		catch (Exception e) { e.printStackTrace(); }
		try{
			strbufip.append('.');
			strbufip.append(jTextField2.getText());
			if(strbufip.toString().trim().length() == 0) return;
		}
		catch (Exception e) { e.printStackTrace(); }
		try{
			strbufip.append('.');
			strbufip.append(jTextField1.getText());
			if(strbufip.toString().trim().length() == 0) return;
		}
		catch (Exception e) { e.printStackTrace(); }
		try{
			strbufip.append('.');
			strbufip.append(jTextField3.getText());
			if(strbufip.toString().trim().length() == 0) return;
			MainFrame.ipAddress = strbufip.toString();
		}
		catch (Exception e) { e.printStackTrace(); }
		try{
			MainFrame.port = jTextField4.getText();	
			if(MainFrame.port.trim().length() == 0) return;
		}
		catch (Exception e) { e.printStackTrace(); }
		this.setModal(false);
		this.setVisible(false);
	}

}
