package view.main;

import controller.UserController;
import controller.exceptions.NotFoundException;
import http.dto.ParamDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.chat.ChatFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

/**
 * Класс основного окна приложения.
 * Реализует шаблон singleton
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainFrame extends JFrame {

    private static final UserController userController = new UserController();

    private static MainFrame instance;

    Box verticalBox = Box.createVerticalBox();
    JButton addChatButton = new JButton();
    JButton serviceButton = new JButton();
    // Мапинг пользователя в окно чата с ним
    HashMap<String, ChatFrame> chatFrames = new HashMap<>();

    private MainFrame() {
        super("SApp");
    }

    /**
     * Метод для получения экземпляра главного окна
     *
     * @return Экземпляр главного окна
     */
    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.build();
        }
        return instance;
    }

    private void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     * Метод для настройки стилей
     */
    private void setComponentsStyle() {
        serviceButton.setPreferredSize(new Dimension(100, 50));
        serviceButton.setText("Test");
        addChatButton.setText("PLUS");
    }

    /**
     * Метод для добавления компонентов
     */
    private void addComponentsToContainer() {
        add(verticalBox, BorderLayout.WEST);
        verticalBox.add(serviceButton);
        verticalBox.add(addChatButton);
    }

    /**
     * Метод для добавления обработчиков
     */
    private void addListeners() {
        // Открытие окна сервиса
        serviceButton.addActionListener(e -> Frame frame = new ServiceFrame());
        // Создание новго чата
        addChatButton.addActionListener(e -> {
            Object result = JOptionPane.showInputDialog(this,
                    "Введите имя пользователя");
            if (result != null) {
                startChat(result.toString().trim());
            }
        });
    }

    /**
     * Метод для открытия окна чата
     *
     * @param name Имя собеседника
     */
    public void startChat(String name) {
        try {
            ParamDto userField = new ParamDto(name);
            User friendUser = userController.getFriendInfo(userField);
            if (chatFrames.containsKey(friendUser.getId())) {
                openChat(friendUser.getId());
            } else {
                startNewChat(friendUser);
            }
        } catch (NotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /**
     * Метод для создания нового окна чата
     *
     * @param friend Пользователь-собеседник
     */
    private void startNewChat(User friend) {
        hideAllChats();
        ChatFrame chat = new ChatFrame(friend);
        JButton chatButton = new JButton();
        chatButton.setText(friend.getName());
        verticalBox.add(chatButton);
        add(chat, BorderLayout.EAST);
        chatFrames.put(friend.getId(), chat);
        chatButton.addActionListener(e -> openChat(friend.getId()));
        chat.build();
    }

    /**
     * Метод для открытия уже существующего окна чата
     *
     * @param friendId Уникальный идентификатор пользователя-собеседника
     */
    private void openChat(String friendId) {
        hideAllChats();
        ChatFrame frame = chatFrames.get(friendId);
        add(frame, BorderLayout.EAST);
        frame.setVisible(true);
    }

    /**
     * Метод для скрытия всех окон чатов
     */
    private void hideAllChats() {
        chatFrames.values().forEach(f -> f.setVisible(false));
    }
}
