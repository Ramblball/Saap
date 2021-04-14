package view;

import javax.swing.*;
import java.awt.*;

public class MainApplicationFrame extends JFrame {
    
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame() {
        int insert = 50;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(insert,
                insert,
                dimension.width - insert * 2,
                dimension.height - insert * 2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    
}