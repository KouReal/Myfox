package Myfox.Client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManinfoUI {

	public JFrame frame;
	JPanel panel1 ;
	
	private JTextField textfield_1;
	
//	public static void main(String[] args) {
//		ManfriendUI test = new ManfriendUI();
//		test.frame.setVisible(true);
//	}
	public ManinfoUI(){
		//初始化图形用户界面
		initialize();
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("查看个人信息");
		// frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeUI.class.getResource("/image/myicon.png")));
		frame.setBounds(100,60,800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		
        panel1 = new JPanel();
        //panel2 = new JPanel();
        //frame.setLayout(new GridLayout(1, 2));
        frame.setContentPane(panel1);
		panel1.setLayout(null);
//        frame.add(panel1);
//        frame.add(panel2);

        JLabel jlb1 = new JLabel("您的id号：" + AccountInfo.accnum);
        jlb1.setFont(new Font("宋体",Font.PLAIN,16));
        //jlb1.setPreferredSize(new Dimension(100,20));
        jlb1.setBounds(100,60,300,30);
		panel1.add(jlb1);
		
		JLabel jlb2 = new JLabel("您的昵称："+AccountInfo.accname);
        jlb2.setFont(new Font("宋体",Font.PLAIN,16));
        //jlb2.setPreferredSize(new Dimension(100,20));
        jlb2.setBounds(100,120,300,30);
		panel1.add(jlb2);
		
		JLabel jlb3 = new JLabel("您的密码：" +AccountInfo.accpwd);
        jlb3.setFont(new Font("宋体",Font.PLAIN,16));
        jlb3.setBounds(100,180,300,30);
		panel1.add(jlb3);
		
		JButton back = new JButton("返回");
		back.setBounds(200,400,100,30);
		//back.setPreferredSize(new Dimension(100,20));
		panel1.add(back);
		back.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				frame.dispose();
				SwitchUI.FunctionUI.setVisible(true);
			}
		});
       
    }

	    
}
