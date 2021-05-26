package service.dating.view;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import service.dating.controller.DatingController;
import service.dating.model.User;

import java.awt.*;
import java.util.Iterator;

import javax.swing.*;

/**
 * Класс главного окна знакомств
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatingFrame extends JFrame implements Frame {

    private static final String BACK_TO_MAIN_MENU = "Назад";
    private static final String CHAT_DENIED_MESSAGE = "Пользователь запретил переписку с ним";
    private static final String NO_MORE_USERS = "В вашем городе больше нет пользователей";

    private static final DatingController datingController = new DatingController();

    private static User user;
    private static Iterator<User> users;

    JPanel informationPanel = new JPanel();
    JButton backButton = new JButton(BACK_TO_MAIN_MENU);
    JButton likeButton = new JButton();
    JButton chatButton = new JButton();
    JButton skipButton = new JButton();
    JLabel userAgeLabel = new JLabel();
    JLabel userCityLabel = new JLabel();
    JLabel userNameLabel = new JLabel();

    public DatingFrame() {
        super("Dating app");
    }

    /**
     * Метод для настройки стилей
     */
    private void setComponentsStyle() {
        getContentPane().setLayout(null);
        informationPanel.setBounds(50, 28, 304, 345);
        informationPanel.setBackground(new Color(196, 196, 196));
        likeButton.setText("LIKE");
        chatButton.setText("CHAT");
        skipButton.setText("SKIP");
        likeButton.setBounds(59, 412, 71, 70);
        chatButton.setBounds(162, 412, 72, 72);
        skipButton.setBounds(273, 412, 72, 72);
        backButton.setBounds(57, 515, 290, 50);
        userNameLabel.setBounds(80, 50, 100, 50);
        userAgeLabel.setBounds(80, 80, 50, 50);
        userCityLabel.setBounds(80, 100, 100, 50);
    }

    /**
     * Метод для добавления компонентов
     */
    private void addComponentsToContainer() {
        add(chatButton);
        add(skipButton);
        add(likeButton);
        add(backButton);
        add(informationPanel);
        informationPanel.add(userNameLabel);
        informationPanel.add(userAgeLabel);
        informationPanel.add(userCityLabel);
    }

    /**
     * Метод для добавления обработчиков
     */
    private void addListeners() {
        // Закрытие
        backButton.addActionListener(e -> dispose());
        // Перебор пользователей
        skipButton.addActionListener(e -> {
            if (users.hasNext()) {
                user = users.next();
                userNameLabel.setText("Имя: " + user.getName());
                userCityLabel.setText("Город: " + user.getCity());
                userAgeLabel.setText("Возраст: " + user.getAge());
            }
            JOptionPane.showMessageDialog(this, NO_MORE_USERS);
        });
        // Открытие чата
        chatButton.addActionListener(e -> {
            if (user != null && !datingController.startChat(user.getName()))
                JOptionPane.showMessageDialog(this, CHAT_DENIED_MESSAGE);
        });
    }

    @Override
    public void build() {
        getUsers();
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 600));
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     * Метод для получения списка пользователей сервиса
     */
    private void getUsers() {
        users = datingController.getUsers().iterator();
    }
}
