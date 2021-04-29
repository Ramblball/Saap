package view.auth;

import controller.exceptions.AuthException;
import view.ViewLiterals;

import javax.swing.*;

/**
 * Класс окна входа
 */
public class LoginFrame extends AuthFrame {
    private final JButton loginButton = new JButton(ViewLiterals.LOGIN_BUTTON);
    private final JButton registryButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);

    public LoginFrame() {
        build();
    }

    @Override
    public void build() {
        // TODO: ?
        super.setComponentsStyle();
        super.addComponentsToContainer();
        super.addListeners();
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();
        super.build();
    }

    @Override
    public void setComponentsStyle() {
        loginButton.setBounds(150, 300, 150, 30);
        registryButton.setBounds(150, 350, 150, 30);
    }

    @Override
    public void addComponentsToContainer() {
        container.add(loginButton);
        container.add(registryButton);
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
                    invokeMain();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
            }
        });
        registryButton.addActionListener(e -> {
            setVisible(false);
            new RegistrationFrame().pack();
        });
    }
}
