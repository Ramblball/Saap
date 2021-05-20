package controller;

import chat.StompClient;
import chat.StompHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.Message;
import model.User;

import javax.swing.*;
import java.util.concurrent.SynchronousQueue;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    // Идентификатор собеседника
    User mate;
    // Экземпляр сокет соединения
    StompClient client;

    public ChatController(User user, JTextArea chatField) {
        mate = user;
        client = StompClient.getInstance();
        // Поток считывающий сообщения собеседника
        Thread messageWaiter = new Thread(() -> {
            SynchronousQueue<Message> queue = StompHandler.getQueue(mate.getId());
            while (true) {
                try {
                    Message message = queue.take();
                    chatField.append(
                            String.format("%s: %s\n", message.getSenderName(), message.getMessage()));
                    log.info(message.toString());
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }, "MESSAGE_WAITER_" + mate.getName());
        messageWaiter.start();
    }

    public void send(String text) {
        Message message = Message.builder()
                .senderId(UserController.getUser().getId())
                .receiverId(mate.getId())
                .senderName(UserController.getUser().getName())
                .receiverName(mate.getName())
                .message(text)
                .build();
        client.send(message);
    }
}
