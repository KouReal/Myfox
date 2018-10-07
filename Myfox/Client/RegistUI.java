//package Myfox.Client;
//import Myfox.ProtoBuf.MessageProbuf;
package Myfox.Client;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;




public class RegistUI{
	public JFrame frame;
	private JTextField textfield_1;
	private JTextField textfield_2;
	private JTextField textfield_3;
	private JPanel contentpane;
	
	
	public RegistUI(){
		//初始化图形用户界面
		initialize();
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("注册Myfox账号");
		// frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeUI.class.getResource("/image/myicon.png")));
		frame.setBounds(100,60,800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentpane = new JPanel();
		//将contentpane对象设置为frame的内容面板
		frame.setContentPane(contentpane);
		contentpane.setLayout(null);
		
		JLabel login_title = new JLabel("填写以下个人信息");
		login_title.setFont(new Font("宋体",Font.PLAIN,16));
		login_title.setBounds(300,10,400,30);
		contentpane.add(login_title);
		
		
		JLabel nickname = new JLabel("昵称");
		nickname.setFont(new Font("宋体",Font.PLAIN,16));
		nickname.setBounds(120,80,60,100);
		contentpane.add(nickname);
		
		JLabel password = new JLabel("密码");
		password.setFont(new Font("宋体",Font.PLAIN,16));
		password.setBounds(120,200,60,100);
		contentpane.add(password);
		
		textfield_1 = new JTextField();
		textfield_1.setBounds(200,120,140,30);
		textfield_1.setColumns(10);
		contentpane.add(textfield_1);
		
		textfield_2 = new JTextField();
		textfield_2.setBounds(200,240,140,30);
		textfield_2.setColumns(10);
		contentpane.add(textfield_2);
		
		
		
		JButton regist = new JButton("分配ID号码");
		regist.setBounds(240,400,160,30);
		contentpane.add(regist);
		regist.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				JLabel wait = new JLabel("正在验证注册信息...");
				wait.setFont(new Font("宋体",Font.PLAIN,16));
				wait.setBounds(200,450,160,100);
				contentpane.add(wait);
				
				String name = textfield_1.getText();
				String pwd = textfield_2.getText();
				int genid = AccountInfo.Regist(name,pwd);
				//sleep()
				if(genid > 0) {
					
					JOptionPane.showMessageDialog(frame, "您的ID:"+ genid);
				}
				else {
					JOptionPane.showMessageDialog(frame, "注册信息有误");
				}
			}
		});
			JButton back = new JButton("返回登录");
			back.setBounds(420,400,160,30);
			contentpane.add(back);
			back.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					frame.dispose();
					SwitchUI.WelcomeUI.setVisible(true);
				}
			});
		
	}

}
