import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebSocket
public class MainWebSocket {
    public static final String REQUEST = "request";
    public static final String RESPONSE = "response";
    public static final String CONNECTION = "connection";

    public static final String SUCCEED = "succeed";
    public static final String FAILED = "failed";

    public static Session session;
    public static ObjectMapper mapper = new ObjectMapper();
    public  MessageHandler messageHandler;
    public  static Map<String, MessageHandler> handlerMap = new LinkedHashMap<>();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("MainWebSocket: onConnect");
        this.session = session;
        messageHandler = new MessageHandler();
        handlerMap.put(REQUEST, messageHandler);
        Message connectMessage = new Message();
        connectMessage.setStatus(SUCCEED);
        connectMessage.setType(CONNECTION);
        sendMessage(connectMessage);
    }

    @OnWebSocketMessage
    public void onText(String jsonMessage) {
        System.out.println("MainWebSocket: onText(String jsonMesage):\njsonMessage:\n" + jsonMessage);
        try {
            Message message = mapper.readValue(jsonMessage, Message.class);
            if(handlerMap.containsKey(message.getType())) {
                handlerMap.get(message.getType()).handle(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("MainWebSocket: onClose()");
        System.err.println(reason+statusCode);
    }

    public static void sendMessage(Message message) {
        try {
            String json = mapper.writeValueAsString(message);
            System.out.println("MainWebSocket: sendMessage: message:\n" + message + "\njson:\n" + json);

            // getRemote() returns a reference to the RemoteEndpoint object representing the other end of this conversation
            // sendString() is a blocking call
            session.getRemote().sendString(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

