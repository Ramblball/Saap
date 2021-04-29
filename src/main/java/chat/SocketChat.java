package chat;

import controller.AuthController;
import lombok.extern.slf4j.Slf4j;
import model.User;

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
    private final static AuthController auth = new AuthController();
    // Адресс сервера
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

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
    private final Map<String, Queue<String>> messages =
            Collections.synchronizedMap(new HashMap<>());

    private SocketChat() {
        try {
            clientSocket = new Socket(HOST, PORT);
            in = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            User user = auth.getUser().get();
            send("init#:#" + user.getName() + "#:#" + user.getId());
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
                if (in.hasNext()) {
                    synchronized (messages) {
                        String[] inMessage = in.nextLine().split(SEPARATOR);
                        String sender = inMessage[0];
                        if (!messages.containsKey(sender)) {
                            messages.put(sender, new PriorityQueue<>());
                        }
                        messages.get(sender).add(inMessage[1]);
                    }
                }
            }
        });
        waiterThread.start();
    }

    public Map<String, Queue<String>> getMessages() {
        return messages;
    }
}
