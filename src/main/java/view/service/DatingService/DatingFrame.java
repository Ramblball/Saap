package view.service.DatingService;

import view.service.DatingService.Frame;
import view.ViewLiterals;

import javax.swing.*;
import java.awt.*;

public class DatingFrame extends JFrame implements Frame {
    private final JPanel informationPanel = new JPanel();
    private final JButton backButton = new JButton(ViewLiterals.BACK_TO_MAIN_MENU);
    private final JButton likeButton = new JButton();
    private final JButton chatButton = new JButton();
    private final JButton skipButton = new JButton();

    public DatingFrame(){
        super("Dating app");
        build();
    }

    @Override
    public void setComponentsStyle() {
        informationPanel.setBounds(50, 28, 304, 345);
        informationPanel.setBackground(new Color(196, 196, 196));
        likeButton.setText("LIKE");
        chatButton.setText("CHAT");
        skipButton.setText("SKIP");
        likeButton.setBounds(59, 412, 71, 70);
        chatButton.setBounds(162, 412, 72, 72);
        skipButton.setBounds(273, 412, 72, 72);
        backButton.setBounds(57, 515, 290, 50);
    }

    @Override
    public void addComponentsToContainer() {
        this.getContentPane().setLayout(null);
        this.add(chatButton);
        this.add(skipButton);
        this.add(likeButton);
        this.add(backButton);
        this.add(informationPanel);
    }

    @Override
    public void addListeners() {
        backButton.addActionListener(e -> {
            dispose();
        });
    }

    @Override
    public void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();
        this.setBounds(400, 400, 400, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
