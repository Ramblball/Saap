package view.chat;

import chat.StompClient;
import controller.ChatController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.Frame;

import javax.swing.*;
import java.awt.*;

/**
 * Класс вложенного окна чата
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatFrame extends JInternalFrame implements Frame {

    public static final String SEND_BUTTON = "Отправить";

    ChatController chatController;

    JTextArea messageTextArea = new JTextArea();
    JTextField enterMessageAskField = new JTextField();
    JScrollPane scrollPane = new JScrollPane(messageTextArea);
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JButton sendButton = new JButton(SEND_BUTTON);

    public ChatFrame(User mate) {
        super(mate.getName());
        chatController = new ChatController(mate, messageTextArea);
    }

    @Override
    public void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        setResizable(false);
        setVisible(true);
    }

    /**
     * Метод для настройки стилей
     */
    private void setComponentsStyle() {
        messageTextArea.setEditable(false);
        messageTextArea.setLineWrap(true);
    }

    /**
     * Метод для добавления компонентов
     */
    private void addComponentsToContainer() {
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.add(enterMessageAskField, BorderLayout.CENTER);
    }

    /**
     * Метод для добавления обработчиков
     */
    private void addListeners() {
        // Отправка сообщения
        sendButton.addActionListener(e -> {
            if (!enterMessageAskField.getText().trim().isEmpty()) {
                sendMessage();
            }
        });
    }

    /**
     * Метод для отправки сообщения
     */
    private void sendMessage() {
        String message = enterMessageAskField.getText().trim();
        chatController.send(message);
        enterMessageAskField.setText("");
    }
}
