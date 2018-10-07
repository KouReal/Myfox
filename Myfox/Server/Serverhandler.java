package Myfox.Server;

import Myfox.ProtoBuf.MessageProbuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class Serverhandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        MessageProbuf.Message mess  = (MessageProbuf.Message)msg;
        System.out.println("server receive : " + mess);
        if(mess.getSendtext().equals("connect") && mess.getReceiverid() == -1){
        	//online assert
        	return ;
        }
        if(mess.getSendtext().equals("logincheck") && mess.getReceiverid() == -1){
        	System.out.println("start logincheck with db");
        	String accname = ManDB.checkaccount(mess.getSenderid(),mess.getSenderpwd());
        	System.out.println("logincheck result:"+accname);
			if( accname != null){
				ManMess.addch(mess.getSenderid(),ctx.channel());
				ctx.channel().writeAndFlush(ManMess.buildmess(-1,"logincheck","no","no","logincheckok",mess.getSenderid(),accname,"no"));
				//ManMess.sendmess(mess.getSenderid(),);
			}
			else{
				ctx.channel().writeAndFlush(ManMess.buildmess(-1,"logincheck","no","no","logincheckerror",mess.getSenderid(),"no","no"));
			}
			return ;
        }

        if(mess.getSendtext().equals("logoffaccount") && mess.getReceiverid() == -1){
        	System.out.println("loginoffaccount :");
        	boolean state = ManMess.removeid(mess.getSenderid());
        	if(state == true){
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"logoffaccount","no","no","logoffok",mess.getSenderid(),mess.getSendername(),"no"));
        	}
        	else{
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"logoffaccount","no","no","logofferror",mess.getSenderid(),mess.getSendername(),"no"));
        	}
        }

        if(mess.getSendtext().equals("registcheck") && mess.getReceiverid() == -1){
        	//no need to check null, client has check null
        	System.out.println("start registcheck with db");
        	int genid = ManDB.addaccount(mess.getSendername(),mess.getSenderpwd());
        	System.out.println("genid : " + genid);
        	if(genid > 0){
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"registcheck","no","no","registcheckok",genid,"no","no"));
        	}
        	else{
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"registcheck","no","no","registcheckerror",genid,"no","no"));
        	}
        	return ;
        }
        if(mess.getSenderid() > 0 && mess.getReceiverid() > 0){
        	if(true == ManMess.sendmess(mess.getReceiverid(),mess)){
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"transmit","no","no","transmitok",mess.getSenderid(),"no","no"));
        	}
        	else{
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"transmit","no","no","transmiterror",mess.getSenderid(),"no","no"));
        	}
        	return ;
        }
        if(mess.getSenderid() > 0 && mess.getReceiverid() == -1){
        	//database service
        	//all result seperated by "==", "++"
        	if(mess.getSendtext().equals("queryfriend")){
        		System.out.println("start queryfriend with db");

        		String result = ManDB.queryfriend(mess.getSenderid());
        		System.out.println("queryfriend result : " + result);

        		if(result == null || result.equals("")){
        			result = "empty";
        		}
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"queryfriend","no","no",result,mess.getSenderid(),mess.getSendername(),mess.getSenderip()));
        	}
        	if(mess.getSendtext().equals("addfriend")){
        		System.out.println("start addfriend with db");

        		boolean state = ManDB.addfriend(mess.getSenderid(),Integer.parseInt(mess.getReceivername()));
        		System.out.println("addfriend state : " + String.valueOf(state));
        		if(state == true){
        			ctx.channel().writeAndFlush(ManMess.buildmess(-1,"addfriend","no","no","addfriendok",mess.getSenderid(),mess.getSendername(),mess.getSenderip()));
        			//notice the friend
        			ManMess.sendmess(Integer.parseInt(mess.getReceivername()),ManMess.buildmess(-1,"passiveaddfriend","no","no",String.valueOf(mess.getSenderid()),Integer.parseInt(mess.getReceivername()),"no","no"));
        		}
        		else{
        			ctx.channel().writeAndFlush(ManMess.buildmess(-1,"addfriend","no","no","addfrienderror",mess.getSenderid(),mess.getSendername(),mess.getSenderip()));
        		}
        	}
        	if(mess.getSendtext().equals("deletefriend")){
        		//wait
        	}
        	if(mess.getSendtext().equals("queryaccount")){
        		//wait
        		System.out.println("start queryaccount with db");
        		String result = ManDB.queryaccount(Integer.parseInt(mess.getReceivername()));
        		System.out.println("queryaccount result : " + result);
        		if(result == null || result.equals("")){
        			result = "empty";
        		}
        		ctx.channel().writeAndFlush(ManMess.buildmess(-1,"queryaccount","no","no",result,mess.getSenderid(),mess.getSendername(),mess.getSenderip()));
        	}
        	//maybe more
        	return ;
        }

    }
    @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //nothing
    }

    // @Override public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    //     Channel ch = ctx.channel();

    // }

    /**
     * 只要有客户端连接就会执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " online\n");
    }

    @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " offline\n");
        ManMess.removech(ctx.channel());
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
