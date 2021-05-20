package chat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import model.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StompClient {

    private static final String URL = "ws://superappserver.herokuapp.com/ws";
    private static final String SEND_PATH = "/app/chat";

    private static StompClient instance;

    WebSocketClient client = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(client);
    StompSessionHandler sessionHandler = new StompHandler();
    @NonFinal StompSession session;

    private StompClient() {
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        connect();
    }

    public static StompClient getInstance() {
        if (instance == null) {
            instance = new StompClient();
        }
        return instance;
    }

    private void connect() {
        try {
            session = stompClient
                    .connect(URL, sessionHandler)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void send(Message message) {
        if (!session.isConnected()) {
            connect();
        }
        session.send(SEND_PATH, message);
    }
}
