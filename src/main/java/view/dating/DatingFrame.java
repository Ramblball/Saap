package view.dating;

import view.Frame;

import javax.swing.*;
import java.awt.*;

public class DatingFrame extends JFrame implements Frame {

    private final Box informationBox = Box.createVerticalBox();
    private final JButton backButton = new JButton("Назад");
    private final JButton likeButton = new JButton();
    private final JButton chatButton = new JButton();
    private final JButton skipButton = new JButton();

    public DatingFrame(){
        super("Dating app");
        build();
    }

    @Override
    public void setComponentsStyle() {
        informationBox.setBounds(50, 38, 304, 345);
        likeButton.setSize(71, 70);
        chatButton.setBounds(164, 415, 72, 72);
        skipButton.setBounds(275, 415, 72, 72);
        backButton.setBounds(57, 518, 290, 50);
    }

    @Override
    public void addComponentsToContainer() {
        add(informationBox);
        add(likeButton);
        add(chatButton);
        add(skipButton);
        add(backButton);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void build() {
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();
        this.setBounds(400, 400, 400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
