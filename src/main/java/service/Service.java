package service;

import chat.StompHandler;
import controller.ServiceController;
import controller.UserController;
import controller.exceptions.NotFoundException;
import http.dto.ParamDto;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.main.MainFrame;

import java.util.List;

/**
 * Класс реализации интерфейса взаимодействия с сервисами
 */
@Slf4j
public class Service implements ServiceMBean {

    private static final String LOCATION_PERMISSION = "LOCATION";
    private static final String CHAT_PERMISSION = "CHAT";

    private static final ServiceController serviceController = new ServiceController();
    private static final UserController userController = new UserController();

    @Override
    public String getLocation(String serviceToken) {
        if (serviceController.hasPermission(serviceToken, LOCATION_PERMISSION)) {
            return UserController.getUser().getCity();
        }
        return null;
    }

    @Override
    public boolean askPermission(String serviceToken, String serviceName, String permission) {
        return serviceController.addPermission(serviceToken, serviceName, permission);
    }

    @Override
    public boolean openChat(String serviceToken, String receiverName) {
        if (serviceController.hasPermission(serviceToken, CHAT_PERMISSION)) {
            try {
                User friend = userController.getFriendInfo(new ParamDto(receiverName));
                StompHandler.getQueue(friend.getId());
                MainFrame.getInstance().startChat(friend.getName());
                return true;
            } catch (NotFoundException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return false;
    }

    @Override
    public List<User> getUsers(String serviceToken, String field, String value) {
        return serviceController.getUsers(serviceToken, field, value);
    }

    @Override
    public boolean hasPermission(String serviceToken, String permission) {
        return serviceController.hasPermission(serviceToken, permission);
    }
}
