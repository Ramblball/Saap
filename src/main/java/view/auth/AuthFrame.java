package view.auth;

import controller.AuthController;
import view.ViewLiterals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AuthFrame extends JFrame implements ActionListener {
    protected static final AuthController auth = new AuthController();

    protected Container container = getContentPane();
    protected JLabel userLabel = new JLabel(ViewLiterals.USER_LABEL);
    protected JLabel passwordLabel = new JLabel(ViewLiterals.PASSWORD_LABEL);
    protected JTextField userTextField = new JTextField();
    protected JPasswordField passwordField = new JPasswordField();
    protected JCheckBox showPassword = new JCheckBox(ViewLiterals.SHOW_PASSWORD);

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

    protected void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);

    }

    protected void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
    }

    protected void addActionEvent() {
        showPassword.addActionListener(this);
    }
}
