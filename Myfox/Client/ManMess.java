package Myfox.Client;

import Myfox.ProtoBuf.MessageProbuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.Channel;

public class ManMess{
        public static Channel ch;

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


        public static void sendmess(MessageProbuf.Message mess){
        
                ch.writeAndFlush(mess);
                
        }

}