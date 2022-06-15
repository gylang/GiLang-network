package com.gilang.network.socket.ws;

import com.gilang.common.domian.DataPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * websocket 协议拼装
 *
 * @author gylang
 * data 2020/11/17
 */
public class WebsocketMessageEncoder extends MessageToMessageEncoder<DataPackage> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DataPackage messageWrap, List<Object> list) throws Exception {

        // 判断协议类型
//        list.add(new TextWebSocketFrame(JSON.toJSONString(messageWrap)));
    }
}
