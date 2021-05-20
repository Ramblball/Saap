package view.main;

import controller.UserController;
import controller.exceptions.NotFoundException;
import http.dto.ParamDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.chat.ChatFrame;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainFrame extends JFrame {

    private static final String JAVA_HOME = System.getenv("JAVA_HOME") + "/bin/java";
    private static final String SERVICE_PATH = "/home/ramblball/Projects/Java/Saap/out/artifacts/test/Saap.jar";

    private static final UserController userController = new UserController();

    private static MainFrame instance;

    Box verticalBox = Box.createVerticalBox();


    JButton addChatButton = new JButton();
    JButton serviceButton = new JButton();

    HashMap<String, ChatFrame> chatFrames = new HashMap<>();

    private MainFrame() {
        super("SApp");
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.build();
        }
        return instance;
    }

    private void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        setResizable(false);
        setVisible(true);
        pack();
    }

    private void setComponentsStyle() {
        serviceButton.setPreferredSize(new Dimension(100, 50));
        serviceButton.setText("Test");
        addChatButton.setText("PLUS");
    }

    private void addComponentsToContainer() {
        add(verticalBox, BorderLayout.WEST);
        verticalBox.add(serviceButton);
        verticalBox.add(addChatButton);
    }

    private void addListeners() {
        serviceButton.addActionListener(e -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(JAVA_HOME, "-jar", SERVICE_PATH);
                Process p = pb.inheritIO().start();
                System.out.println(p.waitFor());
            } catch (IOException | InterruptedException ex) {
                log.error(ex.getMessage(), ex);
            }
        });
        addChatButton.addActionListener(e -> {
            Object result = JOptionPane.showInputDialog(this,
                    "Введите имя пользователя");
            if (result != null) {
                startChat(result.toString().trim());
            }
        });
    }

    public void startChat(String name) {
        try {
            ParamDto userField = new ParamDto(name);
            User friendUser = userController.getFriendInfo(userField);
            if (chatFrames.containsKey(friendUser.getId())) {
                openChat(friendUser.getId());
            } else {
                startNewChat(friendUser);
            }
        } catch (NotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void startNewChat(User friend) {
        hideAllChats();
        ChatFrame chat = new ChatFrame(friend);
        JButton chatButton = new JButton();
        chatButton.setText(friend.getName());
        verticalBox.add(chatButton);
        add(chat, BorderLayout.EAST);
        chatFrames.put(friend.getId(), chat);
        chatButton.addActionListener(e -> openChat(friend.getId()));
        chat.build();
    }

    private void openChat(String friendId) {
        hideAllChats();
        ChatFrame frame = chatFrames.get(friendId);
        add(frame, BorderLayout.EAST);
        frame.setVisible(true);
    }

    private void hideAllChats() {
        chatFrames.values().forEach(f -> f.setVisible(false));
    }
}
