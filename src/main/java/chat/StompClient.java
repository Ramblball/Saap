package chat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import model.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

/**
 * Класс клиента для WebSocket
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StompClient {
    // Url адрес сервера
    private static final String URL = "ws://superappserver.herokuapp.com/ws";
    // Url путь для отправки сообщений
    private static final String SEND_PATH = "/app/chat";

    private static StompClient instance;

    WebSocketClient client = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(client);
    StompSessionHandler sessionHandler = new StompHandler();
    @NonFinal
    StompSession session;

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

    /**
     * Метод для подключения к WS серверу
     */
    private void connect() {
        try {
            session = stompClient
                    .connect(URL, sessionHandler)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Метод для отправки сообщения
     *
     * @param message Сообщение
     */
    public void send(Message message) {
        if (!session.isConnected()) {
            connect();
        }
        session.send(SEND_PATH, message);
    }
}
