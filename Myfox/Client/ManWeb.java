package Myfox.Client;

import Myfox.ProtoBuf.MessageProbuf;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
/**
 * Created by kourui
 * kourui
 * Time: 2018/06/16.
 */
public class ManWeb {
    public static int serverport = 18872;
    public static String serverip = "47.106.92.228";

    public static void connect()throws Exception{
        // 配置NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // Bootstrap 类，是启动NIO服务器的辅助启动类
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception{
                            //
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());

                            ch.pipeline().addLast(new ProtobufDecoder(MessageProbuf.Message.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new Clienthandler());

                        }
                    });

            // 发起异步连接操作
            ChannelFuture f= b.connect(serverip,serverport).sync();

            synchronized(UIWebSyn.connectsyn){
                UIWebSyn.connectover = true;
                UIWebSyn.initstate = true;
                UIWebSyn.connectsyn.notify();
            }

            // 等待客服端链路关闭
            f.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully();
        }
    }

}
