package view.main;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import view.Frame;
import view.IFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceFrame extends Frame implements IFrame {
    private static final String JAVA_HOME = System.getenv("JAVA_HOME") + "/bin/java";

    Container container = getContentPane();

    @Override
    protected void setComponentsStyle() {
        container.setLayout(null);
        File[] listOfFiles = getServices();
        JButton[] btn = new JButton[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; ++i) {
            btn[i] = new JButton(listOfFiles[i].getName());
            btn[i].setBounds(10, i*50, 100, 30);
            addListener(btn[i], listOfFiles[i]);
            container.add(btn[i]);
        }
    }

    @Override
    protected void addComponentsToContainer() {

    }

    @Override
    protected void addListeners() {
    }

    @Override
    public void build() {
        setComponentsStyle();

        setPreferredSize(new Dimension(200, 800));
        setResizable(false);
        setVisible(true);
        pack();
    }

    private File[] getServices() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("services");
        if (url == null) {
            log.error("something going wrong");
            dispose();
        }
        String path = url.getPath();
        return new File(path).listFiles();
    }

    private void addListener(JButton button, File file) {
        button.addActionListener(e -> {
            try{
                ProcessBuilder pb = new ProcessBuilder(JAVA_HOME, "-jar", file.getAbsolutePath());
                Process p = pb.inheritIO().start();
                System.out.println(file.getName());
                System.out.println(p.waitFor());
            } catch (IOException | InterruptedException ex){
                ex.printStackTrace();
            }
        });
    }
}
