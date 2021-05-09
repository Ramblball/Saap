package controller;

import chat.StompClient;
import chat.StompHandler;
import com.google.gson.Gson;
import controller.exceptions.NotFoundException;
import http.Request;
import http.payload.Friend;
import http.request.addChatRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.Message;
import model.User;

import javax.swing.*;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    private static final Gson gson = new Gson();
    // Идентификатор собеседника
    User mate;
    // Экземпляр сокет соединения
    StompClient client;
    // Поток считывающий сообщения собеседника
    Thread messageWaiter;

    public ChatController(User user, JTextArea chatField) {
        mate = user;
        client = StompClient.getInstance();
        messageWaiter = new Thread(() -> {
            SynchronousQueue<Message> queue = StompHandler.getQueue(mate.getId());
            while (true) {
                try {
                    Message message = queue.take();
                    chatField.append(message.getSenderName() + ": " + message.getMessage());
                    chatField.append("\n");
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

    public static User getFriendInfo(Friend friend) throws NotFoundException {
        Request request = new addChatRequest();
        Optional<String> response = request.send(friend);
        if (response.isEmpty()) {
            throw new NotFoundException("Пользователь с таким именем не найден");
        }
        return gson.fromJson(response.get(), User.class);
    }

    public void close() {
        messageWaiter.interrupt();
    }
}
