package view.auth;

import controller.UserController;
import controller.exceptions.AuthException;
import http.dto.LoginDto;
import http.request.PostLogin;
import model.User;
import view.ApplicationRunner;
import view.Frame;

import javax.swing.*;
import java.awt.*;

/**
 * Класс окна входа
 */
public class LoginFrame extends JFrame implements Frame {

    private final UserController auth;

    private final Container container = getContentPane();
    // Лэйбл для поля ввода имени пользователя
    private final JLabel userLabel = new JLabel(AuthLiterals.USER_LABEL);
    // Лэйбл для поля ввода пароля
    private final JLabel passwordLabel = new JLabel(AuthLiterals.PASSWORD_LABEL);
    // Поле для ввода имени пользователя
    private final JTextField userTextField = new JTextField();
    // Поле для ввода пароля
    private final JPasswordField passwordField = new JPasswordField();
    // Чекбокс для отображения и скрытия пароля
    private final JCheckBox showPassword = new JCheckBox(AuthLiterals.SHOW_PASSWORD);

    private final JButton loginButton = new JButton(AuthLiterals.LOGIN_BUTTON);
    private final JButton registryButton = new JButton(AuthLiterals.SIGN_UP_BUTTON);

    public LoginFrame(UserController auth) {
        this.auth = auth;
    }

    @Override
    public void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(370, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    /**
     * Метод для настройки стилей
     */
    private void setComponentsStyle() {
        container.setLayout(null);
        userLabel.setBounds(50, 150, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(150, 300, 150, 30);
        registryButton.setBounds(150, 350, 150, 30);
    }

    /**
     * Метод для добавления компонентов
     */
    private void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(registryButton);
    }

    /**
     * Метод для добавления обработчиков
     */
    private void addListeners() {
        loginButton.addActionListener(e -> {
            String userText = userTextField.getText();
            String passwordText = String.valueOf(passwordField.getPassword());
            if (userText.equals("") || passwordText.equals("")) {
                JOptionPane.showMessageDialog(this, AuthLiterals.EMPTY_FIELDS_DIALOG);
            } else {
                try {
                    User user = auth.authorize(new PostLogin(), new LoginDto(userText, passwordText));
                    if (user != null) {
                        ApplicationRunner.setUser(user);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                    }
                } catch (AuthException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                }
            }
        });
        // Переход к окну регистрации
        registryButton.addActionListener(e -> {
            setVisible(false);
            new RegistrationFrame(new UserController()).build();
        });
        // Инверсия отображения пароля
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
    }
}
