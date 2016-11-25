/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srebrinb.messagebroker.websocket;

import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 *
 * @author sbalabanov
 */
public class MyWebSocketServerProtocolHandler extends WebSocketServerProtocolHandler{
    
    public MyWebSocketServerProtocolHandler(String websocketPath) {
        super(websocketPath);
    }
    
}
