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

@Slf4j
public class StompHandler extends StompSessionHandlerAdapter {

    private static final String MESSAGE_PATH = "/user/%s/queue/messages";

    private static final HashMap<String, SynchronousQueue<Message>> messages = new HashMap<>();

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message message = (Message) payload;
        addMessage(message);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("New session: " + session.getSessionId());
        String url = String.format(MESSAGE_PATH, UserController.getUser().getId());
        session.setAutoReceipt(true);
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

    private void addMessage(Message message) {
        try {
            if (message.getSenderName().equals(UserController.getUser().getName())) {
                if (!messages.containsKey(message.getReceiverId())) {
                    MainFrame.buildInstance().startChat(message.getReceiverId());
                    messages.put(message.getReceiverId(), new SynchronousQueue<>());
                }
                messages.get(message.getReceiverId()).put(message);
            } else {
                if (!messages.containsKey(message.getSenderId())) {
                    MainFrame.buildInstance().startChat(message.getSenderName());
                    messages.put(message.getSenderId(), new SynchronousQueue<>());
                }
                messages.get(message.getSenderId()).put(message);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static SynchronousQueue<Message> getQueue(String id) {
        if (!messages.containsKey(id)) {
            messages.put(id, new SynchronousQueue<>());
        }
        return messages.get(id);
    }
}
