package service.dating.view;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import service.dating.controller.DatingController;
import service.dating.model.User;
import java.awt.Color;
import java.util.Iterator;

import javax.swing.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatingFrame extends JFrame implements Frame {

    public static final String BACK_TO_MAIN_MENU = "Назад";

    private static final String CHAT_DENIED_MESSAGE = "Пользователь запретил переписку с ним";

    private static final DatingController datingController = new DatingController();

    @NonFinal private Iterator<User> users;

    JPanel informationPanel = new JPanel();
    JButton backButton = new JButton(BACK_TO_MAIN_MENU);
    JButton likeButton = new JButton();
    JButton chatButton = new JButton();
    JButton skipButton = new JButton();
    JTextField cityName = new JTextField();

    public DatingFrame() {
        super("Dating app");
    }

    @Override
    public void setComponentsStyle() {
        informationPanel.setBounds(50, 28, 304, 345);
        informationPanel.setBackground(new Color(196, 196, 196));
        likeButton.setText("LIKE"); //Предполагается убрать текст когда на кнопках будут картинки
        chatButton.setText("CHAT");
        skipButton.setText("SKIP");
        likeButton.setBounds(59, 412, 71, 70);
        chatButton.setBounds(162, 412, 72, 72);
        skipButton.setBounds(273, 412, 72, 72);
        backButton.setBounds(57, 515, 290, 50);
        cityName.setBounds(100, 380, 200, 30);
        cityName.setText("Город");
    }

    @Override
    public void addComponentsToContainer() {
        this.getContentPane().setLayout(null);
        this.add(chatButton);
        this.add(skipButton);
        this.add(likeButton);
        this.add(backButton);
        this.add(informationPanel);
        this.add(cityName);
    }

    @Override
    public void addListeners() {
        backButton.addActionListener(e -> dispose());
        skipButton.addActionListener(e -> {
            // TODO:Выведи юзера
            users.next();
        });
        chatButton.addActionListener(e -> {
            if (!datingController.startChat(" ")) { // TODO:Передать id пользоваеля
                JOptionPane.showMessageDialog(this, CHAT_DENIED_MESSAGE);
            }
        });
    }

    @Override
    public void build() {
        getUsers();
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setBounds(400, 400, 400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    private void getUsers() {
        users = datingController.getUsers().iterator();
    }
}
