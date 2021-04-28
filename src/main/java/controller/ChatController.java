package controller;

import chat.SocketChat;

import javax.swing.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Класс описывающий чат с отдельным пользователем
 */
public class ChatController {
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
     * @param chatField         Поле для записи
     */
    public void waitMessages(JTextArea chatField) {
        messageWaiter = new Thread(() -> {
            while (true) {
                synchronized (chat.getMessages()) {
                    SynchronousQueue<String> queue = chat.getMessages().get(mate);
                    while (!queue.isEmpty()) {
                        chatField.append(queue.poll());
                        chatField.append("\n");
                    }
                }
            }
        });
        messageWaiter.start();
    }

    /**
     * Метод для отправки сообщения
     * @param message       Сообщение
     */
    public void sendMessage(String message) {
        //TODO: Добавление идентификатора отпрвителя
        chat.send(mate + "#:#" + message);
    }

    /**
     * Метод для остановки считывания сообщения
     */
    public void close() {
        messageWaiter.interrupt();
    }
}
