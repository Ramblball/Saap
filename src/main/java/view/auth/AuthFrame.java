package view.auth;

import controller.AuthController;
import view.ViewLiterals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Класс окна авторизации
 */
abstract class AuthFrame extends JFrame implements ActionListener {
    protected static final AuthController auth = new AuthController();

    protected Container container = getContentPane();
    // Лэйбл для поля ввода имени пользователя
    protected JLabel userLabel = new JLabel(ViewLiterals.USER_LABEL);
    // Лэйбл для поля ввода пароля
    protected JLabel passwordLabel = new JLabel(ViewLiterals.PASSWORD_LABEL);
    // Поле для ввода имени пользователя
    protected JTextField userTextField = new JTextField();
    // Поле для ввода пароля
    protected JPasswordField passwordField = new JPasswordField();
    // Чекбокс для отображения и скрытия пароля
    protected JCheckBox showPassword = new JCheckBox(ViewLiterals.SHOW_PASSWORD);

    /**
     * Конструктор, задающий параметры окна
     */
    AuthFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(370, 600));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected void setLayoutManager() {
        container.setLayout(null);
    }

    /**
     * Метод для установки параметров размещения элементов в окне
     */
    protected void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);

    }

    /**
     * Метод для добавления элементов к окну
     */
    protected void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
    }

    /**
     * Метод для добавления обработчиков к элементам окна
     */
    protected void addActionEvent() {
        showPassword.addActionListener(this);
    }
}
