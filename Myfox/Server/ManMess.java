package Myfox.Server;

import Myfox.ProtoBuf.MessageProbuf;
import io.netty.channel.Channel;
import java.util.*;

public class ManMess{
        public static Map<Integer,Channel> chmap = new HashMap<Integer,Channel>();

        //Clienthandler.channalActive use senderid to notify server online
        public static int senderid;
        public static String sendername;

		public static MessageProbuf.Message buildmess(int sid,String sn,String sip,String spwd,String stext,int rid,String rn,String rip){
			MessageProbuf.Message.Builder builder = MessageProbuf.Message.newBuilder();
	                builder.setSenderid(sid);
	                builder.setSendername(sn);
	                builder.setSenderip(sip);
	                builder.setSenderpwd(spwd);
	                builder.setSendtext(stext);
	                builder.setReceiverid(rid);
	                builder.setReceivername(rn);
	                builder.setReceiverip(rip);
	                return builder.build();
		}


        public static boolean sendmess(int rid,MessageProbuf.Message mess){
        		Channel ch = chmap.get(rid);
	        	if(ch != null){
	        		ch.writeAndFlush(mess);
	        		return true;
	        	}
	        	else{
	        		return false;
	        	}
                
                
        }
        public static boolean addch(int id,Channel ch){
        	if(chmap.get(id) == null){
        		chmap.put(id,ch);
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        public static Channel id2ch(int id){
        	Channel ch = chmap.get(id);
        	if(ch != null){
        		return ch;
        	}
        	else{
        		return null;
        	}
        }
        public static int ch2id(Channel ch){
			for (int id : chmap.keySet()) {  
			  	if(chmap.get(id) == ch){
			  		return id;
			  	}
			}  
			return 0;
        }
        public static boolean removech(Channel ch){
        	int pos = ch2id(ch);
        	if(pos != 0){
        		chmap.remove(pos);
        		return true;
        	}
        	else{
        		return false;
        	}
        }

        public static boolean removeid(int id){
        	if(chmap.get(id) != null){
        		chmap.remove(id);
        		return true;
        	}
        	else{
        		return false;
        	}
        }

}