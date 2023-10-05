package com.stock.app.business;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class MyWebSocketClient {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        try {
            // WebSocket sunucusuna bağlandığında giriş bilgilerini gönder
            String loginMessage = "{\"_id\": 64, \"user\": \"ozguldigital\", \"password\": \"zgl!8%\", \"info\": \"user_info\", \"resource\": \"default\"}";
            session.getBasicRemote().sendText(loginMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        // WebSocket sunucusundan gelen mesajları burada işleyin
        System.out.println("Received message: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        // WebSocket bağlantısı kapandığında bu metod çağrılır
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // WebSocket ile ilgili hata durumlarını burada işleyin
    }

    public static void main(String[] args) {
        String serverUri = "wss://websocket.foreks.com/websocket";
        try {
            javax.websocket.WebSocketContainer container = javax.websocket.ContainerProvider.getWebSocketContainer();
            container.connectToServer(MyWebSocketClient.class, URI.create(serverUri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
