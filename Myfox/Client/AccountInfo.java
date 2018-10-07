package Myfox.Client;
import Myfox.ProtoBuf.MessageProbuf;

public class AccountInfo {
	public static int accnum = 0;
	public static String accname= new String();
	public static String accpwd= new String();

	
	public static int Regist(String name,String pwd){
		ManMess.sendmess(ManMess.buildmess(0,name,"no",pwd,"registcheck",-1,"server","no"));
		System.out.println("registing account.................");
		MessageProbuf.Message mess;
		while(true){
			synchronized(UIWebSyn.messrwsyn){
				try{
					System.out.println("AccountInfo:regist get syn:");
					mess = MessageBuf.checkservermess("registcheck");
					if(mess != null){
						System.out.println("already get the regist message");
						if(mess.getSendtext().equals("registcheckok")){
							return mess.getReceiverid();
						}
						else{
							return 0;
						}
					}
					System.out.println("AccountInfo:regist wait syn:");
					UIWebSyn.messrwsyn.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
		
	}
	public static String CheckAccount(int accnum,String password){
		ManMess.sendmess(ManMess.buildmess(accnum,"no","no",password,"logincheck",-1,"server","no"));
		System.out.println("AccountInfo:checking account.................");
		MessageProbuf.Message mess;
		while(true){
			synchronized(UIWebSyn.messrwsyn){
				try{
					System.out.println("AccountInfo:checkac get syn:");
					mess = MessageBuf.checkservermess("logincheck");
					if(mess != null){
						System.out.println("already get the CheckAccount message");
						//System.out.println("AccountInfo:checkac get syn:");
						if(mess.getSendtext().equals("logincheckok")){
							return mess.getReceivername();
						}
						else{
							return null;
						}
					}
					System.out.println("AccountInfo:checkac wait syn:");
					UIWebSyn.messrwsyn.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
	}
	public static String queryaccount(int id){
		ManMess.sendmess(ManMess.buildmess(accnum,"no","no","no","queryaccount",-1,String.valueOf(id),"no"));
		System.out.println("queryaccount:.................");
		MessageProbuf.Message mess;
		while(true){
			synchronized(UIWebSyn.messrwsyn){
				try{
					System.out.println("queryaccount:get syn:");
					mess = MessageBuf.checkservermess("queryaccount");
					if(mess != null){
						System.out.println("already get the queryaccount message");
						//System.out.println("AccountInfo:checkac get syn:");
						if(mess.getSendtext().equals("empty")){
							return null;
						}
						else{
							return mess.getSendtext();
						}
					}
					System.out.println("queryaccount: wait syn:");
					UIWebSyn.messrwsyn.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
	}

	public static boolean logoffaccount(int id){
		ManMess.sendmess(ManMess.buildmess(accnum,"no","no","no","logoffaccount",-1,"no","no"));
		System.out.println("logoffaccount:.................");
		MessageProbuf.Message mess;
		while(true){
			synchronized(UIWebSyn.messrwsyn){
				try{
					System.out.println("logoffaccount:get syn:");
					mess = MessageBuf.checkservermess("logoffaccount");
					if(mess != null){
						System.out.println("already get the logoffaccount message");
						//System.out.println("AccountInfo:checkac get syn:");
						if(mess.getSendtext().equals("logoffok")){
							return true;
						}
						else{
							return false;
						}
					}
					System.out.println("logoffaccount: wait syn:");
					UIWebSyn.messrwsyn.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
	}
}
