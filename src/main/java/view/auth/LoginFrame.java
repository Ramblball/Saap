package view.auth;

import controller.exceptions.AuthException;
import view.ViewLiterals;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends AuthFrame {
    private final JButton loginButton = new JButton(ViewLiterals.LOGIN_BUTTON);
    private final JButton registryButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);

    public LoginFrame() {
        super();
        setTitle("login");

        loginButton.setBounds(50, 300, 100, 30);
        registryButton.setBounds(200, 300, 100, 30);

        container.add(loginButton);
        container.add(registryButton);

        loginButton.addActionListener(this);
        registryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
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
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
        if (e.getSource() == registryButton) {
            setVisible(false);
            new RegistrationFrame();
        }
    }
}
