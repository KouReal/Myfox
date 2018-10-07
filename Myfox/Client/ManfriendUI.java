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

public class ManfriendUI {

	public JFrame frame;
	JPanel panel1 ;
	//JPanel panel2;
	private JTextField textfield_1;
	
//	public static void main(String[] args) {
//		ManfriendUI test = new ManfriendUI();
//		test.frame.setVisible(true);
//	}
	public ManfriendUI(){
		//初始化图形用户界面
		initialize();
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("好友管理");
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
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // // 设置选项数据（内部将自动封装成 ListModel ）
        // list.setListData(new String[]{"香蕉", "雪梨", "苹果", "荔枝","香蕉", "雪梨", "苹果", "荔枝"});
        
        new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					String result = ManFriends.queryfriend(AccountInfo.accnum);
					if(result == null){
						list.setListData(new String[]{"暂时没有好友，请搜索添加"});
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
						temparry[i] = "id : "+idstr +"  昵称 : " + nastr +"  状态 : " + state;
						tempinfolist.add(new Friendinfo(Integer.parseInt(idstr),nastr,"no",state));
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

        // 添加选项选中状态被改变的监听器
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 获取所有被选中的选项索引
                int[] indices = list.getSelectedIndices();
                // 获取选项数据的 ListModel
                ListModel<String> listModel = list.getModel();
                // 输出选中的选项
                for (int index : indices) {
                    System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
                }
                System.out.println();
            }
        });
        // 添加到内容面板容器
        panel1.add(list);
        
        JLabel jlb1 = new JLabel("输入陌生人id");
        jlb1.setFont(new Font("宋体",Font.PLAIN,16));
        //jlb1.setPreferredSize(new Dimension(100,20));
        jlb1.setBounds(450,60,200,30);
		panel1.add(jlb1);
		
		textfield_1 = new JTextField();
		textfield_1.setBounds(450,100,200,30);
		//textfield_1.setPreferredSize(new Dimension(100,20));
		textfield_1.setColumns(10);
		panel1.add(textfield_1);
		
		
		
		JLabel jlb2 = new JLabel("查询结果：...");
        //jlb2.setFont(new Font("宋体",Font.PLAIN,16));
        jlb2.setBounds(480,200,250,30);
        //jlb2.setPreferredSize(new Dimension(100,20));
		panel1.add(jlb2);
		//jlb2.setText("helfjlsdjfos");

		JLabel jlb3 = new JLabel("--");
		jlb3.setBounds(480,300,350,30);
		panel1.add(jlb3);

		JButton search = new JButton("找朋友");
		search.setBounds(500,150,80,30);
		//search.setPreferredSize(new Dimension(100, 20));
		panel1.add(search);
		

		search.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(textfield_1.getText() == null || textfield_1.getText().equals("")){
					jlb2.setText("输入不能为空");
					return ;
				}
				jlb3.setText("正在查询........");
				int searchid = Integer.parseInt(textfield_1.getText());
				new Thread(new Runnable(){
						@Override
						public void run(){
						try{
							String result = AccountInfo.queryaccount(searchid);
							if(result == null){
								//list.setListData(new String[]{"找不到这个人"});
								jlb3.setText("找不到这个人");
								return ;
							}
							String tempstr[] = result.split("==");
							String uistr = "id : " + searchid + "  昵称 : " + tempstr[0] + "  状态 : " + tempstr[1];
							jlb2.setText(uistr);
							jlb3.setText("查询完成");
							
							return ;
						}
						catch(Exception e){
							jlb2.setText("查询异常");
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		JButton addfri = new JButton("添加ta");
		addfri.setBounds(500,260,80,30);
		//addfri.setPreferredSize(new Dimension(100,20));
		//search.setPreferredSize(new Dimension(140, 60));
		panel1.add(addfri);
		addfri.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(textfield_1.getText() == null || textfield_1.getText().equals("")){
					jlb2.setText("输入不能为空");
					return ;
				}
				jlb3.setText("稍等，正在为您添加.....");
				int searchid;
				try{
					searchid = Integer.parseInt(textfield_1.getText());
				}catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "输入ID必须是数字");
					return ;
				}
				new Thread(new Runnable(){
						@Override
						public void run(){
						try{
							boolean state = ManFriends.addfriend(AccountInfo.accnum,searchid);
							
							if(state == false){
								JOptionPane.showMessageDialog(frame, "添加失败");
								jlb3.setText("添加失败.....");
								return ;
							}
							JOptionPane.showMessageDialog(frame, "添加成功");
							jlb3.setText("添加成功.....");
							new Thread(new Runnable(){
								@Override
								public void run(){
									try{
										String result = ManFriends.queryfriend(AccountInfo.accnum);
										if(result == null){
											list.setListData(new String[]{"暂时没有好友，请搜索添加"});
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
							return ;
						}
						catch(Exception e){
							jlb3.setText("添加异常");
							e.printStackTrace();
						}
					}
				}).start();
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
