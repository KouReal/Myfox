package Myfox.Client;

import Myfox.ProtoBuf.MessageProbuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
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

public class ChatroomUI {

	public JFrame frame;
	JPanel panel1 ;
	JPanel panel2;
	private JTextField textfield_1;
	
//	public static void main(String[] args) {
//		ManfriendUI test = new ManfriendUI();
//		test.frame.setVisible(true);
//	}
	public ChatroomUI(){
		//初始化图形用户界面
		initialize();
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("聊天室选择好友");
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

        // 创建一个 JList 实例
        final JList<String> list = new JList<String>();

        // 设置一下首选大小
        //list.setPreferredSize(new Dimension(300, 500));
        list.setBounds(10,20,400,550);

        // 允许可间断的多选
        //list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					String result = ManFriends.queryfriend(AccountInfo.accnum);
					if(result == null){
						list.setListData(new String[]{"暂时没有好友，请到“好友管理”添加"});
						return ;
					}
					
					String[] temparry= result.split("(\\+\\+)");
					List<Friendinfo> tempinfolist = new ArrayList<Friendinfo>();
					
					for(int i=0;i<temparry.length;++i){
						String[] elemlist = temparry[i].split("==");
						String idstr = elemlist[0];
						String nastr = elemlist[1];
						String state = "在线";
						if(elemlist[2].equals("0")){
							state = "离线";
						}
						tempinfolist.add(new Friendinfo(Integer.parseInt(idstr),nastr,"no",state));
						temparry[i] = "id : "+idstr +"  昵称 : " + nastr +"  状态 : " + state;
					}
					list.setListData(temparry);
					ManFriends.frilist = new ArrayList<String>(Arrays.asList(temparry));
					ManFriends.friinfolist = tempinfolist;
					return ;
				}
				catch(Exception e){
					list.setListData(new String[]{"查询好友出现了问题"});
					e.printStackTrace();
				}
			}
		}).start();


        
        
        // 添加到内容面板容器
        panel1.add(list);
        
        JLabel jlb1 = new JLabel("从左侧选择一个好友");
        jlb1.setFont(new Font("宋体",Font.PLAIN,16));
        //jlb1.setPreferredSize(new Dimension(100,20));
        jlb1.setBounds(450,60,200,30);
		panel1.add(jlb1);
		
		JLabel jlb2 = new JLabel("好友简介：...");
        jlb2.setFont(new Font("宋体",Font.PLAIN,14));
        //jlb2.setPreferredSize(new Dimension(100,20));
        jlb2.setBounds(450,100,300,30);
		panel1.add(jlb2);

		//JLabel jlb3 = new JLabel("")

		// 添加选项选中状态被改变的监听器
		//Integer selectedfri;
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 获取所有被选中的选项索引
                int[] indices = list.getSelectedIndices();
                // 获取选项数据的 ListModel
                ListModel<String> listModel = list.getModel();
                // 输出选中的选项
                for (int index : indices) {
                	ManFriends.selectedfriend = ManFriends.friinfolist.get(index);
                    jlb2.setText(ManFriends.frilist.get(index));
                    ManFriends.selectedindex = index;

                }
                //System.out.println();
            }
        });
		
		JButton gochat = new JButton("和ta聊天");
		gochat.setBounds(450,150,200,30);
		//search.setPreferredSize(new Dimension(100, 20));
		panel1.add(gochat);
		gochat.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				
				if(ManFriends.selectedindex == -1){
					JOptionPane.showMessageDialog(frame, "你还没有选择好友");
					return ;
				}
				SwitchUI.ChatdialogUI = new ChatdialogUI(ManFriends.selectedfriend).frame;
				frame.dispose();
				SwitchUI.ChatdialogUI.setVisible(true);

			}
		});
		
		JButton back = new JButton("返回");
		back.setBounds(500,400,100,30);
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
