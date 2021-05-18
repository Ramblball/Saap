package view.auth;

import controller.UserController;
import controller.exceptions.AuthException;
import http.payload.LoginReq;
import view.Frame;
import view.IFrame;
import view.ViewLiterals;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Класс окна входа
 */
public class LoginFrame extends Frame implements IFrame {

    protected static final UserController auth = new UserController();

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

    private final JButton loginButton = new JButton(ViewLiterals.LOGIN_BUTTON);
    private final JButton registryButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);

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

    protected void setComponentsStyle() {
        container.setLayout(null);
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(150, 300, 150, 30);
        registryButton.setBounds(150, 350, 150, 30);
    }



    protected void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(registryButton);
    }

    protected void addListeners() {
        loginButton.addActionListener(e -> {
            String userText = userTextField.getText();
            String passwordText = String.valueOf(passwordField.getPassword());
            if (userText.equals("") || passwordText.equals("")) {
                JOptionPane.showMessageDialog(this, ViewLiterals.EMPTY_FIELDS_DIALOG);
            } else {
                try {
                    auth.authorize(
                            LoginReq.builder()
                                    .name(userText)
                                    .password(passwordText)
                                    .build()
                    );
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
        registryButton.addActionListener(e -> {
            setVisible(false);
            new RegistrationFrame().build();
        });
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
    }

    private void invokeMain() {
        SwingUtilities.invokeLater(MainFrame::buildInstance);
        dispose();
    }
}
