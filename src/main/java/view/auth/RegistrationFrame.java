package view.auth;

import controller.exceptions.AuthException;
import view.ViewLiterals;

import javax.swing.*;
import java.util.Objects;

/**
 * Класс окна регистрации
 */
public class RegistrationFrame extends AuthFrame {
    // Диапазон допустимых возрастов
    private static final Integer[] ages = new Integer[88];
    static {
        for (int i = 12; i < 100; i++) {
            ages[i - 12] = i;
        }
    }

    // Лэйбл для поля ввода возраста
    private final JLabel ageLabel = new JLabel(ViewLiterals.AGE_LABEL);
    // Выпадающий список для ввода возраста
    private final JComboBox<Integer> ageBox = new JComboBox<>(ages);
    // Кнопка рагистрации
    private final JButton signupButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);

    private Double age = 12.0;

    public RegistrationFrame() {
        build();
    }

    @Override
    public void build() {
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
        ageLabel.setBounds(50, 185, 100, 30);
        ageBox.setBounds(150, 185, 50, 30);
        signupButton.setBounds(150, 300, 150, 30);
    }

    @Override
    public void addComponentsToContainer() {
        container.add(ageLabel);
        container.add(ageBox);
        container.add(signupButton);
    }

    @Override
    public void addListeners() {
        ageBox.addActionListener(e -> {
            age = ((Integer) Objects.requireNonNull(ageBox.getSelectedItem())).doubleValue();
        });
        signupButton.addActionListener(e -> {
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
                if (auth.getUser().isPresent()) {
                    invokeMain();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid params");
                }
            }
        });
    }
}
