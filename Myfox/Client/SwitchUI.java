package Myfox.Client;

import javax.swing.JFrame;

public class SwitchUI {
	
	public static JFrame WelcomeUI;
	public static JFrame FunctionUI;
	public static JFrame ManfriendUI;
	public static JFrame ManinfoUI;
	public static JFrame RegistUI;
	public static JFrame ChatroomUI;
	public static JFrame ChatdialogUI;
	
	public static void Dispose_Frame(JFrame frame){
		frame.dispose();
	}
	public static void Visulize_Frame(JFrame frame){
		frame.setVisible(true);
	}
	
	public static void Active_WelcomeUI(){
		WelcomeUI.setVisible(true);
	}
	public static void Active_FunctionUI(){
		FunctionUI.setVisible(true);
	}
}
