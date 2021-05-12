package view.main;

import controller.ChatController;
import controller.exceptions.NotFoundException;
import http.payload.Friend;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.Frame;
import view.chat.ChatFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainFrame extends JFrame implements Frame {

    Box serviceVBox = Box.createVerticalBox();
    Box chatVBox = Box.createVerticalBox();

    JButton addChatButton = new JButton();
    JButton datingButton = new JButton();

    HashMap<String, ChatFrame> chatFrames = new HashMap<>();

    @Override
    public void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        setResizable(false);
        setVisible(true);
        pack();
    }

    @Override
    public void setComponentsStyle() {
        try {
            setButtonStyle(datingButton,
                    getClass().getClassLoader().getResource("images/defB.png").getFile(),
                    getClass().getClassLoader().getResource("images/toB.png").getFile());
        } catch (NullPointerException ex) {
            log.error(ex.getMessage(), ex);
            System.exit(0);
        }
        addChatButton.setText("PLUS");
        chatVBox.setSize(new Dimension(100, 400));
    }

    private void setButtonStyle(JButton button, String imagePath, String pressedImagePath) {
        button.setIcon(new ImageIcon(imagePath));
        button.setPressedIcon(new ImageIcon(pressedImagePath));
        button.setSize(10, 10);

        // Убираем все ненужные рамки и закраску
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setHideActionText(false);
    }

    @Override
    public void addComponentsToContainer() {
        add(serviceVBox, BorderLayout.WEST);
        add(chatVBox);
        chatVBox.add(addChatButton);
        serviceVBox.add(datingButton);
    }

    @Override
    public void addListeners() {
        datingButton.addActionListener(e -> log.info("user open dating"));
        addChatButton.addActionListener(e -> {
            Object result = JOptionPane.showInputDialog(this,
                    "Введите имя пользователя");
            if (result != null) {
                openChat(result.toString().trim());
            }
        });
    }

    private void openChat(String friendName) {
        Friend friend = new Friend(friendName);
        User user;
        try {
            user = ChatController.getFriendInfo(friend);
        } catch (NotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }
        chatFrames.values().forEach(f -> f.setVisible(false));
        if (chatFrames.containsKey(user.getId())) {
            ChatFrame frame = chatFrames.get(user.getId());
            add(frame, BorderLayout.EAST);
            frame.setVisible(true);
        } else {
            ChatFrame chat = new ChatFrame(user);
            add(chat, BorderLayout.EAST);
            chatFrames.put(user.getId(), chat);
            chat.build();
            chat.toFront();
        }
    }
}
