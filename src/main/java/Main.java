import lombok.extern.slf4j.Slf4j;
import view.Frame;
import view.auth.LoginFrame;

import javax.swing.*;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Sapp started");
        SwingUtilities.invokeLater(() -> {
            Frame frame = new LoginFrame();
            frame.build();
        });
    }
}
