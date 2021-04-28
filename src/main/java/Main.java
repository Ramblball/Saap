import view.chat.ChatFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatFrame("").pack();
        });
        System.out.println("test");
    }
}
