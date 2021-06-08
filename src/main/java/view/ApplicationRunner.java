package view;

import controller.UserController;
import lombok.extern.slf4j.Slf4j;
import model.User;
import service.Service;
import view.auth.LoginFrame;
import view.main.MainFrame;

import javax.management.*;
import javax.swing.*;
import java.lang.management.ManagementFactory;

@Slf4j
public class ApplicationRunner {

    private static MainFrame mainFrame;
    private static volatile User user;

    public static void init() {
        authorize();
        initMBeanServer();
        while (user == null) {
            Thread.onSpinWait();
        }
        invokeMain();
    }

    /**
     * Метод для открытия окна авторизации
     */
    private static void authorize() {
        SwingUtilities.invokeLater(() -> {
            Frame frame = new LoginFrame(new UserController());
            frame.build();
        });
    }

    /**
     * Метод для запуска JMX сервера
     */
    private static void initMBeanServer() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("org.example:type=Service");
            Service serviceBean = new Service();
            mbs.registerMBean(serviceBean, name);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
    }

    /**
     * Метод для открытия главного окна приложения
     */
    private static void invokeMain() {
        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame(new UserController());
            mainFrame.build();
        });
    }

    public static void setUser(User user) {
        ApplicationRunner.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
}
