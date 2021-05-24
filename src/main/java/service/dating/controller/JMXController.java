package service.dating.controller;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * Класс контроллер для jmx соединения.
 * Реализует шаблон singleton
 */
@Slf4j
public class JMXController {

    private static final String HOST = "localhost";
    private static final int PORT = 1234;
    private static final String URL =
            String.format("service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi", HOST, PORT);

    private static JMXController instance;
    private ServiceMBean bean;

    private JMXController() {
    }

    public static JMXController getInstance() throws IOException, MalformedObjectNameException {
        if (instance == null) {
            instance = new JMXController();
            instance.connection();
        }
        return instance;
    }

    /**
     * Метод для подключения к основному приложению по jmx
     *
     * @throws IOException                  Ошибка разрыва соединения
     * @throws MalformedObjectNameException Ошибка в имени интерфейса
     */
    private void connection() throws IOException, MalformedObjectNameException {
        JMXServiceURL serviceURL = new JMXServiceURL(URL);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, null);
        MBeanServerConnection mbeanConn = jmxConnector.getMBeanServerConnection();
        ObjectName mbeanName = new ObjectName("org.example:type=Service");

        bean = MBeanServerInvocationHandler
                .newProxyInstance(mbeanConn, mbeanName, ServiceMBean.class, true);
    }

    public ServiceMBean getBean() {
        return bean;
    }
}
