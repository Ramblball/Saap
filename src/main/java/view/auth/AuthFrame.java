package view.auth;

import controller.AuthController;
import view.Frame;
import view.ViewLiterals;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Класс окна авторизации
 */
public abstract class AuthFrame extends JFrame implements Frame {
    protected static final AuthController auth = new AuthController();

    protected Container container = getContentPane();
    // Лэйбл для поля ввода имени пользователя
    private final JLabel userLabel = new JLabel(ViewLiterals.USER_LABEL);
    // Лэйбл для поля ввода пароля
    private final JLabel passwordLabel = new JLabel(ViewLiterals.PASSWORD_LABEL);
    // Поле для ввода имени пользователя
    protected JTextField userTextField = new JTextField();
    // Поле для ввода пароля
    protected JPasswordField passwordField = new JPasswordField();
    // Чекбокс для отображения и скрытия пароля
    protected JCheckBox showPassword = new JCheckBox(ViewLiterals.SHOW_PASSWORD);

    AuthFrame() {
        if (auth.getUser().isPresent()) {
            invokeMain();
        }
    }

    @Override
    public void build() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(370, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void setComponentsStyle() {
        container.setLayout(null);
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
    }

    @Override
    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
    }

    @Override
    public void addListeners() {
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
    }

    protected void invokeMain() {
        SwingUtilities.invokeLater(() -> {
            MainFrame main = new MainFrame();
            main.pack();
        });
        dispose();
    }
}
