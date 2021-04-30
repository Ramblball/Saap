package controller;

import chat.SocketChat;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.Queue;

/**
 * Класс описывающий чат с отдельным пользователем
 */
@Slf4j
public class ChatController {
    private final static AuthController auth = new AuthController();
    // Идентификатор собеседника
    private final String mate;
    // Экземпляр сокет соединения
    private final SocketChat chat;
    // Поток считывающий сообщения собеседника
    private Thread messageWaiter;

    public ChatController(String mate) {
        this.mate = mate;
        chat = SocketChat.getInstance();
    }

    /**
     * Метод для считывания сообщения и записи в соответствующее поле
     *
     * @param chatField Поле для записи
     */
    public void waitMessages(JTextArea chatField) {
        messageWaiter = new Thread(() -> {
            while (true) {
                Queue<String> queue = chat.getMessages().get(mate);
                if (queue == null) {
                    continue;
                }
                while (!queue.isEmpty()) {
                    chatField.append(mate + ": " + queue.poll());
                    chatField.append("\n");
                }
                log.debug(chatField.getText());
            }
        });
        messageWaiter.start();
    }

    /**
     * Метод для отправки сообщения
     *
     * @param message Сообщение
     */
    public void sendMessage(String message) {

        chat.send("msg#:#" + mate + "#:#" + message);
    }

    /**
     * Метод для остановки считывания сообщения
     */
    public void close() {
        messageWaiter.interrupt();
    }
}
