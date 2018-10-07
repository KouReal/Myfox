package Myfox.Client;

import Myfox.ProtoBuf.MessageProbuf;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class Clienthandler extends ChannelInboundHandlerAdapter {
    public Clienthandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ManMess.ch = ctx.channel();
        ManMess.sendmess(ManMess.buildmess(0,"no","no","no","connect",-1,"no","no"));
    }

    // private MessageProbuf.Message PReq(int id) {
    //     MessageProbuf.Message.Builder builder = MessageProbuf.Message.newBuilder();
    //     builder.setSenderid(id);
    //     builder.setSendername("orange");
    //     return builder.build();
    // }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProbuf.Message mess  = (MessageProbuf.Message)msg;
        System.out.println("client receive : " + mess);
        synchronized(UIWebSyn.messrwsyn){
                
                MessageBuf.addmess(mess);
                UIWebSyn.messrwsyn.notify();
        }
        
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
