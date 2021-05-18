package service.dating.controller;

import lombok.extern.slf4j.Slf4j;
import service.dating.exception.JMXException;
import service.dating.model.Permission;
import service.dating.model.User;

import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DatingController {

    private static final String TOKEN = "";
    private static final String NAME = "Dating";

    private JMXController jmxController;

    public DatingController() {
        try {
            jmxController = JMXController.getInstance();
        } catch (IOException | MalformedObjectNameException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    public List<User> getUsers() {
        try {
            if (hasPermission(Permission.LOCATION) || askPermission(Permission.LOCATION)) {
                return jmxController
                        .getBean()
                        .getUsers(TOKEN, "city", getLocation().orElseThrow(JMXException::new));
            }
        } catch (JMXException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
        return new ArrayList<>();
    }

    public boolean startChat(String receiverId) {
        try {
            if (hasPermission(Permission.CHAT) || askPermission(Permission.CHAT)) {
                openChat(receiverId);
                return true;
            }
        } catch (JMXException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    private Optional<String> getLocation() {
        String city = jmxController.getBean().getLocation(TOKEN);
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(city);
    }

    private void openChat(String receiverId) throws JMXException {
        if (jmxController.getBean().openChat(TOKEN, receiverId)) {
            throw new JMXException();
        }
    }

    private boolean hasPermission(Permission permission) {
        return jmxController.getBean()
                .hasPermission(TOKEN, permission.toString());
    }

    private boolean askPermission(Permission permission) {
        return jmxController.getBean()
                .askPermission(TOKEN, NAME, permission.toString());
    }
}
