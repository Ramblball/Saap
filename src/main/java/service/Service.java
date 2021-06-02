package service;

import chat.StompHandler;
import controller.ServiceController;
import controller.UserController;
import controller.exceptions.NotFoundException;
import http.dto.CriteriaDto;
import http.dto.ParamDto;
import http.dto.ServiceParamDto;
import http.request.GetServiceUsers;
import http.request.GetUser;
import http.request.PutAddPermission;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.ApplicationRunner;

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
            return ApplicationRunner.getUser().getCity();
        }
        return null;
    }

    @Override
    public boolean askPermission(String serviceToken, String serviceName, String permission) {
        return serviceController.addPermission(new PutAddPermission(), new ServiceParamDto(serviceToken, permission), serviceName);
    }

    @Override
    public boolean openChat(String serviceToken, String receiverName) {
        if (serviceController.hasPermission(serviceToken, CHAT_PERMISSION)) {
            try {
                User friend = userController.getFriendInfo(new GetUser(), new ParamDto(receiverName));
                StompHandler.getQueue(friend.getId());
                ApplicationRunner.getMainFrame().startChat(friend.getName());
                return true;
            } catch (NotFoundException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return false;
    }

    @Override
    public String getUsers(String serviceToken, String field, String value) {
        return serviceController.getUsers(new GetServiceUsers(), new CriteriaDto(serviceToken, field, value));
    }

    @Override
    public boolean hasPermission(String serviceToken, String permission) {
        return serviceController.hasPermission(serviceToken, permission);
    }
}
