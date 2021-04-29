package chat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Класс открытия сокет соединения
 * Реализует Singleton
 */
@Slf4j
public class SocketChat {
    // Адресс сервера
    private static final String HOST = "https://superappserver.herokuapp.com";
    private static final int PORT = 8081;

    private static final String CLOSE_COMMAND = "SystemSessionEnd";
    private static final String SEPARATOR = "#:#";

    // Экземпляр соединения
    private static SocketChat instance;
    // Поток считывающий сообщения
    private static Thread waiterThread;

    private Socket clientSocket;
    private Scanner in;
    private PrintWriter out;

    // Все полученные сообщения, распределенные по отправителям
    private final Map<String, SynchronousQueue<String>> messages =
            Collections.synchronizedMap(new HashMap<>());

    private SocketChat() {
        try {
            clientSocket = new Socket(HOST, PORT);
            in = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            instance = this;
            waitMessages();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static SocketChat getInstance() {
        return Objects.requireNonNullElseGet(instance, SocketChat::new);
    }

    // Метод для отправки сообщений
    public void send(String message) {
        out.println(message);
    }

    // Метод для закрытия соединения
    public void close() {
        try {
            out.println(CLOSE_COMMAND);
            waiterThread.interrupt();
            out.close();
            in.close();
            clientSocket.close();
            instance = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для запуска потока считывания сообщений
    private void waitMessages() {
        waiterThread = new Thread(() -> {
            while (true) {
                synchronized (messages) {
                    if (in.hasNext()) {
                        String[] inMessage = in.nextLine().split(SEPARATOR);
                        String user = inMessage[0];
                        if (!messages.containsKey(user)) {
                            messages.put(user, new SynchronousQueue<>());
                        }
                        try {
                            messages.get(user).put(inMessage[1]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        waiterThread.start();
    }

    public Map<String, SynchronousQueue<String>> getMessages() {
        return messages;
    }
}
