/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package srebrinb.messagebroker.websocket;

import com.google.gson.Gson;
import io.netty.channel.ChannelFuture;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static srebrinb.messagebroker.websocket.WebSocketServer.allChannels;

/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);
    private static final String regex = "(\\w*)\\((.*)\\);?";
    private static final Pattern funcPattern = Pattern.compile(regex);
    Gson gson = new Gson();
    private String VERSION_NUMBER="11.1";
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx)throws Exception {
        System.out.println("channelInactive "+ctx.channel().id().asShortText());
        srebrinb.messagebroker.WebSockerMessager.unregUser(ctx.channel().id().asLongText());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ChannelFuture f = ctx.channel().writeAndFlush(new TextWebSocketFrame("(gameID=1)[LiveOthelloServer="+ VERSION_NUMBER + "]\n"));
        System.out.println("channelActive "+ctx.channel().id().asShortText());
        
    }
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered "+ctx.channel().id().asShortText());
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            String res="channelId=\""+ctx.channel().id().asLongText()+"\";";
            ChannelFuture f = ctx.channel().writeAndFlush(new TextWebSocketFrame(res));
                                    
            System.out.println("userEventTriggered ServerHandshakeStateEvent ");
            
        }
        ctx.fireUserEventTriggered(evt);
        
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            // (\w*)\((.*)\);?
            Matcher m = funcPattern.matcher(request);
            String func = "";
            String strParams = "";
            if (m.matches()) {
                if (m.groupCount() == 2) {
                    func = m.group(1);
                    strParams = m.group(2);
                }
                try {
                    Object o = gson.fromJson(strParams, Object.class);
                } catch (Exception ex) {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame("exception(\"" + ex.getMessage() + "\")"));
                }
            }
            System.out.println(func + ":" + strParams);
            if (func.equals("reg")){
                System.out.println("call reg");
                allChannels.add(ctx.channel());
                srebrinb.messagebroker.WebSockerMessager.regUser(strParams, ctx.channel().id().asLongText());
                
            }
            logger.info("{} received {}", ctx.channel(), request);
            ctx.channel().writeAndFlush(new TextWebSocketFrame("//"+request.toString()));
        
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
