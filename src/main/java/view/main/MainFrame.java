package view.main;

import controller.AuthController;
import lombok.extern.slf4j.Slf4j;
import view.Frame;
import view.chat.ChatFrame;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainFrame extends JFrame implements Frame {
    private static final AuthController authController = new AuthController();

    private final Box box = Box.createVerticalBox();

    private final JButton datingButton = new JButton();

    public MainFrame() {
        super("SApp");
        build();
    }

    @Override
    public void setComponentsStyle() {
        // TODO: ?
        setButtonStyle(datingButton,
                getClass().getClassLoader().getResource("images/defB.png").getFile(),
                getClass().getClassLoader().getResource("images/toB.png").getFile());
    }

    private void setButtonStyle(JButton button, String imagePath, String pressedImagePath) {
        button.setIcon(new ImageIcon(imagePath));
        button.setPressedIcon(new ImageIcon(pressedImagePath));
        button.setSize(10, 10);

        // Убираем все ненужные рамки и закраску
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setHideActionText(false);
    }

    @Override
    public void addComponentsToContainer() {
        add(box);
        box.add(datingButton);
        add(new ChatFrame(""), BorderLayout.EAST);
    }

    @Override
    public void addListeners() {
        datingButton.addActionListener(e -> {
            log.info("user open dating");
        });
    }

    @Override
    public void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        setResizable(false);
        setVisible(true);
    }
}
