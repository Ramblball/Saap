package view;

import controller.OpenDating;
import controller.OpenMesseger;

import javax.swing.*;

public class MainButton {
    public Box ButtonStyles() {
        Box box = Box.createVerticalBox();

        JButton button = new JButton();
        button.setIcon(new ImageIcon("Images\\defMess.png"));
        button.setPressedIcon(new ImageIcon("Images\\toMess.png"));
        button.setSize(10, 10);


        button.addActionListener(new OpenMesseger());
        // Убираем все ненужные рамки и закраску
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setHideActionText(false);
        box.add(button);

        button = new JButton();
        button.setIcon(new ImageIcon("Images\\defB.png"));
        button.setPressedIcon(new ImageIcon("Images\\toB.png"));
        button.setSize(10, 10);

        button.addActionListener(new OpenDating());
        // Убираем все ненужные рамки и закраску
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setHideActionText(false);
        box.add(button);
        return box;
    }
}
