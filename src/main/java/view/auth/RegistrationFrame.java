package view.auth;

import controller.exceptions.AuthException;
import view.ViewLiterals;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class RegistrationFrame extends AuthFrame {
    private static final Integer[] ages = new Integer[88];
    static {
        for (int i = 12; i < 100; i++) {
            ages[i - 12] = i;
        }
    }

    private final JLabel userLabel = new JLabel(ViewLiterals.AGE_LABEL);
    private final JComboBox<Integer> ageBox = new JComboBox<>(ages);
    private final JButton signupButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);

    private Double age;

    public RegistrationFrame() {
        super();
        setTitle("reg");

        userLabel.setBounds(50, 185, 100, 30);
        ageBox.setBounds(150, 185, 50, 30);
        signupButton.setBounds(150, 300, 100, 30);

        container.add(userLabel);
        container.add(ageBox);
        container.add(signupButton);

        ageBox.addActionListener(this);
        signupButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        } else if (e.getSource() == ageBox) {
            age = ((Integer) Objects.requireNonNull(ageBox.getSelectedItem())).doubleValue();
        } else if (e.getSource() == signupButton) {
            String userText = userTextField.getText();
            String passwordText = String.valueOf(passwordField.getPassword());
            if (userText.equals("") || passwordText.equals("") || age == null) {
                JOptionPane.showMessageDialog(this, ViewLiterals.EMPTY_FIELDS_DIALOG);
            } else {
                try {
                    auth.register(userText, passwordText, age);
                } catch (AuthException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                }
                //TODO: Заменить на переход на главное окно
                if (auth.getUser().isPresent()) {
                    JOptionPane.showMessageDialog(this, "Registration Successful");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid params");
                }
            }
        }
    }
}
