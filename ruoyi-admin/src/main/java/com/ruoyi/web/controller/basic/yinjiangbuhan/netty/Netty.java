package com.ruoyi.web.controller.basic.yinjiangbuhan.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Netty {
    public static String pushSwzk(Object obj){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            // 添加处理器或编解码器等
                        }
                    });
            //:9045
            // 连接到远程服务器
            ChannelFuture future = bootstrap.connect("oa.sntsoft.com", 9045).sync();

            log.info("push netty tcp swzk:{}",JSON.toJSONString(obj));

            // 可以在这里发送数据
           future.channel().writeAndFlush(obj);
            // 等待连接关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
        return null;
    }
}
