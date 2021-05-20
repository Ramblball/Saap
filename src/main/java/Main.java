import lombok.extern.slf4j.Slf4j;
import service.Service;
import view.Frame;
import view.auth.LoginFrame;

import javax.management.*;
import javax.swing.*;
import java.lang.management.ManagementFactory;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            log.info("Sapp started");
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("org.example:type=Service");
            Service serviceBean = new Service();
            mbs.registerMBean(serviceBean, name);
            Frame frame = new LoginFrame();
            SwingUtilities.invokeLater(frame::build);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
    }
}
