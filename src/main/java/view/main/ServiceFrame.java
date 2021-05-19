package view.main;

import view.Frame;
import view.IFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ServiceFrame extends Frame implements IFrame {
    private static final String JAVA_HOME = System.getenv("JAVA_HOME") + "/bin/java";

    Container container = getContentPane();

    @Override
    protected void setComponentsStyle() {
        container.setLayout(null);
        JButton[] btn = new JButton[10];

        String path = System.getProperty("user.dir") + "\\out\\artifacts\\Saap_jar";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; ++i) {
            btn[i] = new JButton(listOfFiles[i].getName());
            btn[i].setBounds(10, i*50, 100, 30);
            int finalI = i;
            btn[i].addActionListener(e -> {
                        Runtime re = Runtime.getRuntime();
                        try{
                            ProcessBuilder pb = new ProcessBuilder(JAVA_HOME, "-jar", path+listOfFiles[finalI].getName());
                            Process p = pb.inheritIO().start();
                            System.out.println(listOfFiles[finalI].getName());
                            System.out.println(p.waitFor());
                        } catch (IOException | InterruptedException ex){
                            ex.printStackTrace();
                        }
                    });
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
        addComponentsToContainer();
        addListeners();

        setPreferredSize(new Dimension(200, 800));
        setResizable(false);
        setVisible(true);
        pack();
    }
}
