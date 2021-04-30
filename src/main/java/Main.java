import lombok.extern.slf4j.Slf4j;
import view.auth.AuthFrame;
import view.auth.LoginFrame;

import javax.swing.*;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Sapp started");
        SwingUtilities.invokeLater(() -> {
            AuthFrame frame = new LoginFrame();
            frame.pack();
        });
    }
}
