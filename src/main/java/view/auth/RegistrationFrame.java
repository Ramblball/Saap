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
    private final JLabel userLabel = new JLabel(ViewLiterals.AGE_LABEL);
    // Выпадающий список для ввода возраста
    private final JComboBox<Integer> ageBox = new JComboBox<>(ages);
    // Кнопка рагистрации
    private final JButton signupButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);

    private Double age = 12.0;

    public RegistrationFrame() {
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
        userLabel.setBounds(50, 185, 100, 30);
        ageBox.setBounds(150, 185, 50, 30);
        signupButton.setBounds(150, 300, 100, 30);
    }

    @Override
    public void addComponentsToContainer() {
        add(userLabel);
        add(ageBox);
        add(signupButton);
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
                //TODO: Заменить на переход на главное окно
                if (auth.getUser().isPresent()) {
                    JOptionPane.showMessageDialog(this, "Registration Successful");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid params");
                }
            }
        });
    }
}
