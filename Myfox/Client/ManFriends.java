package Myfox.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import Myfox.ProtoBuf.MessageProbuf;

public class ManFriends{
	public static List<String> frilist = new ArrayList<String>();
	public static List<Friendinfo> friinfolist = new ArrayList<Friendinfo>();
	public static Friendinfo selectedfriend = new Friendinfo();
	public static int selectedindex = -1;
	public static String queryfriend(int sid){
		ManMess.sendmess(ManMess.buildmess(sid,"no","no","no","queryfriend",-1,"server","no"));
		System.out.println("ManFriends:queryfriend.................");
		MessageProbuf.Message mess;
		while(true){
			synchronized(UIWebSyn.messrwsyn){
				try{
					System.out.println("ManFriends:queryfriend get syn:");
					mess = MessageBuf.checkservermess("queryfriend");
					if(mess != null){
						System.out.println("already get the queryfriend message");
						//System.out.println("AccountInfo:checkac get syn:");
						if(mess.getSendtext().equals("empty")){
							return null;
						}
						else{
							return mess.getSendtext();
						}
					}
					System.out.println("ManFriends:queryfriend wait syn:");
					UIWebSyn.messrwsyn.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
	}
	public static boolean addfriend(int sid,int rid){
		ManMess.sendmess(ManMess.buildmess(sid,"no","no","no","addfriend",-1,String.valueOf(rid),"no"));
		System.out.println("ManFriends:addfriend.................");
		MessageProbuf.Message mess;
		while(true){
			synchronized(UIWebSyn.messrwsyn){
				try{
					System.out.println("ManFriends:addfriend get syn:");
					mess = MessageBuf.checkservermess("addfriend");
					if(mess != null){
						System.out.println("already get the addfriend message");
						//System.out.println("AccountInfo:checkac get syn:");
						if(mess.getSendtext().equals("addfriendok")){
							return true;
						}
						else{
							return false;
						}
					}
					System.out.println("ManFriends:addfriend wait syn:");
					UIWebSyn.messrwsyn.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
	}

	public static boolean sendchatmess(MessageProbuf.Message mess){
		System.out.println("ManFriends:sendchatmess:"+mess);
		
		synchronized(UIWebSyn.messrwsyn){
			try{
				System.out.println("ManFriends:sendchatmess ok");
				ManMess.sendmess(mess);
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}



	public static int passiveaddfriend(int sid){
		int rid = 0;
		MessageProbuf.Message mess;
		synchronized(UIWebSyn.messrwsyn){
			mess = MessageBuf.checkservermess("passiveaddfriend");
			if(mess != null){
				rid = Integer.parseInt(mess.getSendtext());
			}
		}
		return rid;
	}
}