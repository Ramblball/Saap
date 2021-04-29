package view.chat;

import controller.ChatController;
import lombok.extern.slf4j.Slf4j;
import view.Frame;
import view.ViewLiterals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Класс вложенного окна чата
 */
@Slf4j
public class ChatFrame extends JInternalFrame implements Frame {

    private final ChatController chatController;

    private final JTextArea messageTextArea = new JTextArea();
    private final JTextField enterMessageAskField = new JTextField(ViewLiterals.ENTER_MESSAGE_FIELD);

    private final JScrollPane scrollPane = new JScrollPane(messageTextArea);
    private final JPanel bottomPanel = new JPanel(new BorderLayout());

    private final JButton sendButton = new JButton(ViewLiterals.SEND_BUTTON);

    public ChatFrame(String mate) {
        chatController = new ChatController(mate);
        chatController.waitMessages(messageTextArea);

        build();
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

    @Override
    public void setComponentsStyle() {
        messageTextArea.setEditable(false);
        messageTextArea.setLineWrap(true);
    }

    @Override
    public void addComponentsToContainer() {
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.add(enterMessageAskField, BorderLayout.CENTER);
    }

    @Override
    public void addListeners() {
        sendButton.addActionListener(e -> {
            if (!enterMessageAskField.getText().trim().isEmpty()) {
                sendMessage();
            }
        });
        enterMessageAskField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                enterMessageAskField.setText("");
            }
        });
    }

    private void sendMessage() {
        String message = enterMessageAskField.getText().trim();
        chatController.sendMessage(message);
        enterMessageAskField.setText("");
    }
}
