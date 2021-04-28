package view.auth;

import controller.exceptions.AuthException;
import view.Frame;
import view.ViewLiterals;

import javax.swing.*;

/**
 * Класс окна входа
 */
public class LoginFrame extends AuthFrame implements Frame {
    private final JButton loginButton = new JButton(ViewLiterals.LOGIN_BUTTON);
    private final JButton registryButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);

    public LoginFrame() {
        super();
        build();
    }

    @Override
    public void build() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addListeners();
    }

    @Override
    public void setLocationAndSize() {
        loginButton.setBounds(50, 300, 100, 30);
        registryButton.setBounds(200, 300, 100, 30);
    }

    @Override
    public void addComponentsToContainer() {
        add(loginButton);
        add(registryButton);
    }

    @Override
    public void addListeners() {
        loginButton.addActionListener(e -> {
            String userText = userTextField.getText();
            String passwordText = String.valueOf(passwordField.getPassword());
            if (userText.equals("") || passwordText.equals("")) {
                JOptionPane.showMessageDialog(this, ViewLiterals.EMPTY_FIELDS_DIALOG);
            } else {
                try {
                    auth.authorize(userText, passwordText);
                } catch (AuthException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                }
                if (auth.getUser().isPresent()) {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
            }
        });
        registryButton.addActionListener(e -> {
            setVisible(false);
            new RegistrationFrame();
        });
    }
}
