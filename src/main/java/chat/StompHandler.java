package chat;

import controller.UserController;
import lombok.extern.slf4j.Slf4j;
import model.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import view.main.MainFrame;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * Класс обработчик кадров WebSocket
 */
@Slf4j
public class StompHandler extends StompSessionHandlerAdapter {

    // Url путь для получения сообщений
    private static final String MESSAGE_PATH = "/user/%s/queue/messages";

    private static final HashMap<String, SynchronousQueue<Message>> messages = new HashMap<>();

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        addMessage((Message) payload);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("New session: " + session.getSessionId());
        String url = String.format(MESSAGE_PATH, UserController.getUser().getId());
        session.subscribe(url, this);
        log.info("Subscribe to: " + url);
    }

    @Override
    public void handleException(
            StompSession session, StompCommand command,
            StompHeaders headers, byte[] payload, Throwable exception) {
        log.error(exception.getMessage(), exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error(exception.getMessage(), exception);
    }

    /**
     * Метод для распределения сообщений по пользователям
     *
     * @param message Сообщение
     */
    private void addMessage(Message message) {
        try {
            if (message.getSenderName().equals(UserController.getUser().getName())) {
                if (!messages.containsKey(message.getReceiverId())) {
                    addQueue(message.getReceiverId());
                    MainFrame.getInstance().startChat(message.getReceiverName());
                }
                getQueue(message.getReceiverId()).put(message);
            } else {
                if (!messages.containsKey(message.getSenderId())) {
                    addQueue(message.getSenderId());
                    MainFrame.getInstance().startChat(message.getSenderName());
                }
                getQueue(message.getSenderId()).put(message);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void addQueue(String id) {
        messages.put(id, new SynchronousQueue<>());
    }

    public static SynchronousQueue<Message> getQueue(String id) {
        if (!messages.containsKey(id)) {
            messages.put(id, new SynchronousQueue<>());
        }
        return messages.get(id);
    }
}
