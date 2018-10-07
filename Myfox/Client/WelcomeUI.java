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




public class WelcomeUI{
	public JFrame frame;
	private JTextField textfield_1;
	private JTextField textfield_2;
	private JPanel contentpane;
	private JRadioButton normaluser;
	private JRadioButton administrator;
	
	public static void main(String[] args) {

		WelcomeUI window = new WelcomeUI();
		SwitchUI.WelcomeUI = window.frame;
		// window.frame.setVisible(true);
		SwitchUI.Visulize_Frame(SwitchUI.WelcomeUI);
	}
	
	public WelcomeUI(){
		initialize();
		
		//initialize();
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("登录Myfox");
		// frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeUI.class.getResource("/image/myicon.png")));
		frame.setBounds(100,60,800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentpane = new JPanel();
		//将contentpane对象设置为frame的内容面板
		frame.setContentPane(contentpane);
		contentpane.setLayout(null);
		

		JLabel weblabel = new JLabel("");
		weblabel.setFont(new Font("宋体",Font.PLAIN,16));
		weblabel.setBounds(10,480,250,30);
		contentpane.add(weblabel);

		//for weblabel : no news is good news

		//launch the webthread
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					ManWeb.connect();
				}
				catch(Exception e){
					synchronized(UIWebSyn.connectsyn){
						UIWebSyn.connectover = true;
						UIWebSyn.initstate = false;
						weblabel.setText("网络连接异常，请检查网络设置");
					}
					e.printStackTrace();
				}
			}
		}).start();
		
		

		

		JLabel login_title = new JLabel("Myfox登录");
		login_title.setFont(new Font("宋体",Font.PLAIN,16));
		login_title.setBounds(300,10,400,30);
		contentpane.add(login_title);
		
		ImageIcon img = new ImageIcon("./image/IM.png");//创建图片对象
		JLabel image_label = new JLabel();
		image_label.setBounds(320,50,400,400);
		image_label.setIcon(img);
		contentpane.add(image_label);
		
		JLabel account = new JLabel("ID号码");
		account.setFont(new Font("宋体",Font.PLAIN,16));
		account.setBounds(40,80,60,100);
		contentpane.add(account);
		
		JLabel password = new JLabel("密码");
		password.setFont(new Font("宋体",Font.PLAIN,16));
		password.setBounds(40,200,60,100);
		contentpane.add(password);
		
		textfield_1 = new JTextField();
		textfield_1.setBounds(120,120,140,30);
		textfield_1.setColumns(10);
		contentpane.add(textfield_1);
		
		textfield_2 = new JTextField();
		textfield_2.setBounds(120,240,140,30);
		textfield_2.setColumns(10);
		contentpane.add(textfield_2);
		
		
		
		JButton regist = new JButton("注册");
		regist.setBounds(120,400,80,30);
		contentpane.add(regist);
		regist.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				SwitchUI.RegistUI = new RegistUI().frame;
				SwitchUI.Dispose_Frame(SwitchUI.WelcomeUI);
				SwitchUI.Visulize_Frame(SwitchUI.RegistUI);
			}
				
		});
		
		
		JButton login = new JButton("登录");
		login.setBounds(260,400,80,30);
		contentpane.add(login);
		login.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				
				String account = textfield_1.getText();
				String password = textfield_2.getText();
				if(account.equals("")||account==null){
					JOptionPane.showMessageDialog(frame, "您好，用户名不能为空");
					return ;
				}else if(password.equals("")||password==null){
					JOptionPane.showMessageDialog(frame, "您好，密码不能为空");
					return ;
				}
				int accnum;
				try{
					accnum = Integer.parseInt(account);
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "输入ID必须是系统分配的数字");
					return ;
				}
				
				String accname = AccountInfo.CheckAccount(accnum, password);
				if(accname == null){
					JOptionPane.showMessageDialog(frame, "用户或密码错误");
				}
				else{
					AccountInfo.accnum = accnum;
					AccountInfo.accname = accname;
					AccountInfo.accpwd = password;
					//JOptionPane.showMessageDialog(frame, "登录成功!");
					frame.dispose();
					SwitchUI.FunctionUI = new FunctionUI().frame;
					SwitchUI.FunctionUI.setVisible(true);
					
				}
						
			}
		
		});
		
		//作者信息
		JLabel authorinfo = new JLabel();
		authorinfo.setBounds(400,480,300,30);
		authorinfo.setFont(new Font("宋体",Font.PLAIN,14));
		authorinfo.setText("寇瑞-2015302555-10011501-软件工程实验");
		contentpane.add(authorinfo);
	}

}
