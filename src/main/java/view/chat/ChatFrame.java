package view.chat;

import controller.ChatController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.IFrame;
import view.ViewLiterals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Класс вложенного окна чата
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatFrame extends JInternalFrame implements IFrame {

    ChatController chatController;

    JTextArea messageTextArea = new JTextArea();
    JTextField enterMessageAskField = new JTextField();

    JScrollPane scrollPane = new JScrollPane(messageTextArea);
    JPanel bottomPanel = new JPanel(new BorderLayout());

    JButton sendButton = new JButton(ViewLiterals.SEND_BUTTON);

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

    protected void setComponentsStyle() {
        messageTextArea.setEditable(false);
        messageTextArea.setLineWrap(true);
    }

    protected void addComponentsToContainer() {
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.add(enterMessageAskField, BorderLayout.CENTER);
    }

    protected void addListeners() {
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
        chatController.send(message);
        enterMessageAskField.setText("");
    }
}
