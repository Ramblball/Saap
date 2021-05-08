import lombok.extern.slf4j.Slf4j;
import view.auth.LoginFrame;

import javax.swing.*;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Sapp started");
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.build();
        });
    }
}
