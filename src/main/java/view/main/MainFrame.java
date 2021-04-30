package view.main;

import lombok.extern.slf4j.Slf4j;
import view.Frame;
import view.chat.ChatFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@Slf4j
public class MainFrame extends JFrame implements Frame {
    private final Box serviceBox = Box.createVerticalBox();
    private final Box chatBox = Box.createVerticalBox();

    private final JButton addChatButton = new JButton();
    private final JButton datingButton = new JButton();

    private final HashMap<String, ChatFrame> chatFrames = new HashMap<>();

    public MainFrame() {
        super("SApp");
        build();
    }

    @Override
    public void setComponentsStyle() {
        try {
            setButtonStyle(datingButton,
                    getClass().getClassLoader().getResource("images/defB.png").getFile(),
                    getClass().getClassLoader().getResource("images/toB.png").getFile());
        } catch (NullPointerException ex) {
            log.error(ex.getMessage(), ex);
            System.exit(0);
        }
        addChatButton.setText("PLUS");
        addChatButton.setSize(new Dimension(50, 50));
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
        add(serviceBox);
//        add(chatBox);
//        chatBox.add(addChatButton);
        serviceBox.add(datingButton);
        add(new ChatFrame("6089f9e14147ed4ca3ce267f"), BorderLayout.EAST);
    }

    @Override
    public void addListeners() {
        datingButton.addActionListener(e -> {
            log.info("user open dating");
        });
        addChatButton.addActionListener(e -> {
            //TODO: ToFront
            ChatFrame newChat = new ChatFrame("608a0f0ab1b22905e236d990");
            chatFrames.put("608a0f0ab1b22905e236d990", newChat);
            add(newChat, BorderLayout.EAST);
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
