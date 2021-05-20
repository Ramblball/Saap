package view.auth;

import controller.UserController;
import controller.exceptions.AuthException;
import http.dto.LoginDto;
import view.Frame;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Класс окна входа
 */
public class LoginFrame extends JFrame implements Frame {

    protected static final UserController auth = new UserController();

    protected Container container = getContentPane();
    // Лэйбл для поля ввода имени пользователя
    private final JLabel userLabel = new JLabel(AuthLiterals.USER_LABEL);
    // Лэйбл для поля ввода пароля
    private final JLabel passwordLabel = new JLabel(AuthLiterals.PASSWORD_LABEL);
    // Поле для ввода имени пользователя
    protected JTextField userTextField = new JTextField();
    // Поле для ввода пароля
    protected JPasswordField passwordField = new JPasswordField();
    // Чекбокс для отображения и скрытия пароля
    protected JCheckBox showPassword = new JCheckBox(AuthLiterals.SHOW_PASSWORD);

    private final JButton loginButton = new JButton(AuthLiterals.LOGIN_BUTTON);
    private final JButton registryButton = new JButton(AuthLiterals.SIGN_UP_BUTTON);

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
                    auth.authorize(new LoginDto(userText, passwordText));
                } catch (AuthException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                }
                if (UserController.getUser() != null) {
                    invokeMain();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
            }
        });
        // Переход к окну регистрации
        registryButton.addActionListener(e -> {
            setVisible(false);
            new RegistrationFrame().build();
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

    /**
     * Метод для открытия главного окна приложения
     */
    private void invokeMain() {
        SwingUtilities.invokeLater(MainFrame::getInstance);
        dispose();
    }
}
