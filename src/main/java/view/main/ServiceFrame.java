package view.main;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import view.Frame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс окна отображения сервисов
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceFrame extends JFrame implements Frame {
    private static final String JAVA_HOME = System.getenv("JAVA_HOME") + "/java";

    private static final Map<String, Process> processes = new HashMap<>();

    Container container = getContentPane();

    protected void setComponentsStyle() {
        container.setLayout(null);
        File[] services = getServices();
        for (int i = 0; i < services.length; i++) {
            JButton btn = new JButton(services[i].getName().replace(".jar", ""));
            btn.setBounds(10, i * 50, 150, 30);
            addListener(btn, services[i]);
            container.add(btn);
        }
    }

    @Override
    public void build() {
        setComponentsStyle();

        setPreferredSize(new Dimension(200, 800));
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     * Метод для получения массива сервисов из resources
     *
     * @return Массив сервисов
     */
    private File[] getServices() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("services");
        if (url == null) {
            log.error("Something going wrong");
            dispose();
            return new File[0];
        }
        String path = url.getPath();
        return new File(path).listFiles();
    }

    private void addListener(JButton button, File file) {
        button.addActionListener(e -> {
            if (!processes.containsKey(file.getName()) || !processes.get(file.getName()).isAlive()) {
                try {
                    ProcessBuilder pb = new ProcessBuilder(JAVA_HOME, "-jar", file.getAbsolutePath());
                    Process process = pb.inheritIO().start();
                    processes.put(file.getName(), process);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void stopServices() {
        processes.values().forEach(Process::destroy);
    }
}
