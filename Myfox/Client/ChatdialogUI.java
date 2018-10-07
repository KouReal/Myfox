package Myfox.Client;

import Myfox.ProtoBuf.MessageProbuf;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class ChatdialogUI {
	public Friendinfo friendinfo;
	public JTextField textfield_1 = new JTextField();
	public JFrame frame;
	public JPanel panel1;
	public JLabel[] jlbs;
	public int messlimit;
	public int tempmesslen;
	
//	public static void main(String[] args) {
//		ChatdialogUI test = new ChatdialogUI();
//		test.frame.setVisible(true);
//	}
	
	public ChatdialogUI(Friendinfo fi){
		this.friendinfo = fi;
		this.messlimit = 25;
		this.tempmesslen = 0;
		UIWebSyn.chatstate = true;
		//初始化图形用户界面
		initialize();
	}
	
	public void initialize(){
		messlimit = 25;
		jlbs = new JLabel[messlimit];
		tempmesslen = 0;
		frame = new JFrame();
		frame.setTitle("发送消息");
		// frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeUI.class.getResource("/image/myicon.png")));
		frame.setBounds(100,10,800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1 = new JPanel();
		frame.setContentPane(panel1);
		panel1.setLayout(null);
		
		for(int i=0;i<messlimit;++i) {
			jlbs[i] = new JLabel();
			jlbs[i].setText("");
			jlbs[i].setFont(new Font("宋体",Font.PLAIN,16));
			jlbs[i].setBounds(10,20+20*i,500,15);
			panel1.add(jlbs[i]);
		}
		
		textfield_1.setBounds(10,550,500,30);
		textfield_1.setColumns(35);
		panel1.add(textfield_1);

		JLabel lab0 = new JLabel();
		lab0.setFont(new Font("宋体",Font.PLAIN,16));
		lab0.setBounds(520,60,200,20);
		panel1.add(lab0);
		lab0.setText("对方简介");

		JButton sendmess = new JButton("发送");
		sendmess.setBounds(520,550,160,30);
		panel1.add(sendmess);
		sendmess.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				String sendtext = textfield_1.getText();
				if(UIWebSyn.chatstate == false){
						return ;
				}
				if(sendtext==null || sendtext.equals("")){
					JOptionPane.showMessageDialog(frame, "您好，发送消息不能为空");
					return ;
				}
				//
				MessageProbuf.Message tempsendmess = ManMess.buildmess(AccountInfo.accnum,AccountInfo.accname,"no","no",sendtext,friendinfo.id,friendinfo.name,"no");
				ManFriends.sendchatmess(tempsendmess);
				if(UIWebSyn.chatstate == false){
						return ;
				}
				synchronized(UIWebSyn.chatuisyn){
					System.out.println("send button get chatuisyn");
					if(tempmesslen < messlimit){
						jlbs[tempmesslen].setText(AccountInfo.accname + " : " + sendtext);
						tempmesslen++;
					}
					else{
						for(int i=0;i<messlimit;++i) {
							
							jlbs[i].setText("");
							
						}
						jlbs[0].setText(AccountInfo.accname + " : " + sendtext);
						tempmesslen=1;
					}
					System.out.println("send button release chatuisyn");
				}
				
			}
		});

		JButton back = new JButton("返回");
		back.setBounds(520,200,160,30);
		panel1.add(back);
		back.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				//notice the receive thread to stop
				UIWebSyn.chatstate = false;
				tempmesslen = 0;
				for(int i=0;i<messlimit;++i) {
					
					jlbs[i].setText("");
					
				}
				frame.dispose();
				SwitchUI.ChatroomUI.setVisible(true);
			}
		});

		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					while(true){
						try{
							if(UIWebSyn.chatstate == false){
								break;
							}
							MessageProbuf.Message mess;
							synchronized(UIWebSyn.messrwsyn){
								mess = MessageBuf.checkchatmess(friendinfo.id);
								if(mess == null){
									UIWebSyn.messrwsyn.wait();
								}
							}
							if(UIWebSyn.chatstate == false){
								break;
							}
							if(mess != null){
								System.out.println("get chat message : " + mess);
								String receivetext = mess.getSendtext();
								if(UIWebSyn.chatstate == false){
									break;
								}
								synchronized(UIWebSyn.chatuisyn){
									System.out.println("receive thread get chatuisyn");
									if(tempmesslen < messlimit){
										jlbs[tempmesslen].setText(friendinfo.name + " : " + receivetext);
										tempmesslen++;
									}
									else{
										for(int i=0;i<messlimit;++i) {
										
											jlbs[i].setText("");
											
										}
										jlbs[0].setText(friendinfo.name + " : " + receivetext);
										tempmesslen = 1;
									}
									System.out.println("receive thread release chatuisyn");
								}
								if(UIWebSyn.chatstate == false){
									break;
								}
							}
						}
						catch(InterruptedException e){
							e.printStackTrace();
						}
						
					}
				}
				catch(Exception e){
					lab0.setText(new String("读取聊天信息出现了问题"));
					e.printStackTrace();
				}
			}
		}).start();


		
		
		JLabel lab2 = new JLabel();
		lab2.setFont(new Font("宋体",Font.PLAIN,14));
		lab2.setBounds(520,150,270,20);
		panel1.add(lab2);
		lab2.setText(ManFriends.frilist.get(ManFriends.selectedindex));
		
		
		
		
	}
        
}
