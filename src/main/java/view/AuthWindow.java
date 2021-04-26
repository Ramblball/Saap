package view;

import controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthWindow extends JFrame implements ActionListener {
    private static final AuthController auth = new AuthController();

    private Container container = getContentPane();
    private JLabel userLabel = new JLabel("USERNAME");
    private JLabel passwordLabel = new JLabel("PASSWORD");
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("LOGIN");
    private JCheckBox showPassword = new JCheckBox("Show Password");
    private JButton registryButton = new JButton("SIGN UP");

    public AuthWindow() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        registryButton.setBounds(200, 300, 100, 30);

    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(registryButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        showPassword.addActionListener(this);
        registryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = String.valueOf(passwordField.getPassword());
            auth.authorize(userText, pwdText);
            if (auth.getUser().isPresent()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
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
            //TODO: Open Registry page
        }
    }
}
