package Myfox.Client;
import Myfox.ProtoBuf.MessageProbuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
public class MessageBuf{
	public static List<MessageProbuf.Message> chatmessbuf = new ArrayList<MessageProbuf.Message>();
	public static List<MessageProbuf.Message> servermessbuf = new ArrayList<MessageProbuf.Message>();
	public static int chatlimit = 30;
	public static int serverlimit = 30;
	public static int chatlen = 0;
	public static int serverlen = 0;

	public static boolean addmess(MessageProbuf.Message mess){
		//mess from friends,used in chatroom
		if(mess.getSenderid() > 0){
			if(chatlen < chatlimit){
				chatmessbuf.add(mess);
				chatlen++;
				return true;
			}
		}
		//mess from server,used in querydb service
		if(mess.getSenderid() == -1){
			if(serverlen < serverlimit){
				servermessbuf.add(mess);
				serverlen++;
				return true;
			}
		}
		return false;
	}
	public static MessageProbuf.Message checkchatmess(int senderid){
		Iterator<MessageProbuf.Message> it = chatmessbuf.iterator();
        while(it.hasNext()){
            MessageProbuf.Message mess = (MessageProbuf.Message)it.next();
            int sid = mess.getSenderid();
            if(senderid == sid){
                it.remove();
                chatlen--;
                return mess;
            }        
        }
        return null;
	}
	public static MessageProbuf.Message checkservermess(String messstr){
		Iterator<MessageProbuf.Message> it = servermessbuf.iterator();
        while(it.hasNext()){
            MessageProbuf.Message mess = (MessageProbuf.Message)it.next();
            String stxt = mess.getSendername();
            if(stxt.equals(messstr)){
                it.remove();
                serverlen--;
                return mess;
            }        
        }
        return null;
	}
    
        

}