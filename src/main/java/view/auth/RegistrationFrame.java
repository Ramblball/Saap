package view.auth;

import controller.UserController;
import controller.exceptions.AuthException;
import http.dto.RegisterDto;
import view.Frame;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Класс окна регистрации
 */
public class RegistrationFrame extends JFrame implements Frame {

    protected static final UserController auth = new UserController();
    // Диапазон допустимых возрастов
    private static final Integer[] ages = new Integer[88];
    static {
        for (int i = 12; i < 100; i++) {
            ages[i - 12] = i;
        }
    }

    private final Container container = getContentPane();
    // Лэйбл для поля ввода имени пользователя
    private final JLabel userLabel = new JLabel(AuthLiterals.USER_LABEL);
    // Лэйбл для поля ввода имени пользователя
    private final JLabel cityLabel = new JLabel(AuthLiterals.CITY_LABEL);
    // Поле для ввода имени пользователя
    protected JTextField cityTextField = new JTextField();
    // Лэйбл для поля ввода возраста
    private final JLabel ageLabel = new JLabel(AuthLiterals.AGE_LABEL);
    // Выпадающий список для ввода возраста
    private final JComboBox<Integer> ageBox = new JComboBox<>(ages);
    // Кнопка рагистрации
    private final JButton signupButton = new JButton(AuthLiterals.SIGN_UP_BUTTON);
    // Лэйбл для поля ввода пароля
    private final JLabel passwordLabel = new JLabel(AuthLiterals.PASSWORD_LABEL);
    // Поле для ввода имени пользователя
    protected JTextField userTextField = new JTextField();
    // Поле для ввода пароля
    protected JPasswordField passwordField = new JPasswordField();
    // Чекбокс для отображения и скрытия пароля
    protected JCheckBox showPassword = new JCheckBox(AuthLiterals.SHOW_PASSWORD);

    private Integer age = 12;

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
        userLabel.setBounds(50, 115, 100, 30);
        userTextField.setBounds(150, 115, 150, 30);
        cityLabel.setBounds(50, 150, 100, 30);
        cityTextField.setBounds(150, 150, 150, 30);
        ageLabel.setBounds(50, 185, 100, 30);
        ageBox.setBounds(150, 185, 50, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        signupButton.setBounds(150, 300, 150, 30);
    }

    protected void addComponentsToContainer() {
        container.add(userLabel);
        container.add(userTextField);
        container.add(cityLabel);
        container.add(cityTextField);
        container.add(ageLabel);
        container.add(ageBox);
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(showPassword);
        container.add(signupButton);
    }

    protected void addListeners() {
        ageBox.addActionListener(e -> age = (Integer) ageBox.getSelectedItem());
        signupButton.addActionListener(e -> {
            String userText = userTextField.getText();
            String cityText = cityTextField.getText();
            String passwordText = String.valueOf(passwordField.getPassword());
            if (userText.equals("") || cityText.equals("") || passwordText.equals("") || age == null) {
                JOptionPane.showMessageDialog(this, AuthLiterals.EMPTY_FIELDS_DIALOG);
            } else {
                try {
                    auth.register(
                            RegisterDto.builder()
                                    .name(userText)
                                    .password(passwordText)
                                    .city(cityText)
                                    .age(age).build()
                    );
                } catch (AuthException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                }
                if (UserController.getUser() != null) {
                    invokeMain();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid params");
                }
            }
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
        SwingUtilities.invokeLater(MainFrame::getInstance);
        dispose();
    }
}
