package view.main;

import controller.ChatController;
import controller.UserController;
import controller.exceptions.NotFoundException;
import http.payload.Friend;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.Frame;
import view.chat.ChatFrame;
import view.service.weather.APIOpenWeather;
import view.service.weather.MainWeather;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainFrame extends JFrame implements Frame {

    Box serviceVBox = Box.createVerticalBox();
    Box chatVBox = Box.createVerticalBox();

    private final JButton addChatButton = new JButton();
    private final JButton datingButton = new JButton();
    private final JButton weatherButton = new JButton();

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
        datingButton.setText("dating");
        addChatButton.setSize(new Dimension(50, 50));
        addChatButton.setText("PLUS");
        addChatButton.setSize(new Dimension(50, 50));
        weatherButton.setText("weather");
        weatherButton.setSize(new Dimension(50, 50));
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
        serviceVBox.add(weatherButton);
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
        weatherButton.addActionListener(e -> {
            MainWeather.main();
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
