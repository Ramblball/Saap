package view.auth;

import controller.UserController;
import controller.exceptions.AuthException;
import http.payload.Register;
import view.Frame;
import view.ViewLiterals;
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
    private final JLabel userLabel = new JLabel(ViewLiterals.USER_LABEL);
    // Лэйбл для поля ввода имени пользователя
    private final JLabel cityLabel = new JLabel(ViewLiterals.CITY_LABEL);
    // Поле для ввода имени пользователя
    protected JTextField cityTextField = new JTextField();
    // Лэйбл для поля ввода возраста
    private final JLabel ageLabel = new JLabel(ViewLiterals.AGE_LABEL);
    // Выпадающий список для ввода возраста
    private final JComboBox<Integer> ageBox = new JComboBox<>(ages);
    // Кнопка рагистрации
    private final JButton signupButton = new JButton(ViewLiterals.SIGN_UP_BUTTON);
    // Лэйбл для поля ввода пароля
    private final JLabel passwordLabel = new JLabel(ViewLiterals.PASSWORD_LABEL);
    // Поле для ввода имени пользователя
    protected JTextField userTextField = new JTextField();
    // Поле для ввода пароля
    protected JPasswordField passwordField = new JPasswordField();
    // Чекбокс для отображения и скрытия пароля
    protected JCheckBox showPassword = new JCheckBox(ViewLiterals.SHOW_PASSWORD);

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

    @Override
    public void setComponentsStyle() {
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

    @Override
    public void addComponentsToContainer() {
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

    @Override
    public void addListeners() {
        ageBox.addActionListener(e -> age = (Integer) ageBox.getSelectedItem());
        signupButton.addActionListener(e -> {
            String userText = userTextField.getText();
            String cityText = cityTextField.getText();
            String passwordText = String.valueOf(passwordField.getPassword());
            if (userText.equals("") || cityText.equals("") || passwordText.equals("") || age == null) {
                JOptionPane.showMessageDialog(this, ViewLiterals.EMPTY_FIELDS_DIALOG);
            } else {
                try {
                    auth.register(
                            Register.builder()
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
        SwingUtilities.invokeLater(() -> {
            MainFrame main = new MainFrame();
            main.build();
        });
        dispose();
    }
}
