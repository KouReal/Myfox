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




public class FunctionUI{
	public JFrame frame;
	private JTextField textfield_1;
	private JTextField textfield_2;
	private JTextField textfield_3;
	private JPanel contentpane;
	
	
	public FunctionUI(){
		//初始化图形用户界面
		initialize();
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("选择功能");
		// frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeUI.class.getResource("/image/myicon.png")));
		frame.setBounds(100,60,800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentpane = new JPanel();
		//将contentpane对象设置为frame的内容面板
		frame.setContentPane(contentpane);
		contentpane.setLayout(null);
		
			
		JButton manfri = new JButton("好友管理");
		manfri.setBounds(200,200,120,30);
		contentpane.add(manfri);
		manfri.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				frame.dispose();
				SwitchUI.ManfriendUI = new ManfriendUI().frame;
				SwitchUI.ManfriendUI.setVisible(true);
			}
		});
		
		JButton chatroom = new JButton("聊天室");
		chatroom.setBounds(200,250,120,30);
		contentpane.add(chatroom);
		chatroom.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				frame.dispose();
				SwitchUI.ChatroomUI = new ChatroomUI().frame;
				SwitchUI.ChatroomUI.setVisible(true);
			}
		});
		
		JButton maninfo = new JButton("设置个人信息");
		maninfo.setBounds(200,300,180,30);
		contentpane.add(maninfo);
		maninfo.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				frame.dispose();
				SwitchUI.ManinfoUI = new ManinfoUI().frame;
				SwitchUI.ManinfoUI.setVisible(true);
			}
		});
			JButton back = new JButton("切换账号");

			//切换账号后，服务器需要移除当前账号的channel(),wait

			back.setBounds(200,350,180,30);
			contentpane.add(back);
			back.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					if(true == AccountInfo.logoffaccount(AccountInfo.accnum)){
						frame.dispose();
						SwitchUI.WelcomeUI.setVisible(true);
					}
					else{
						JOptionPane.showMessageDialog(frame, "切换账号异常");
					}
					
				}
			});
		
	}

}
