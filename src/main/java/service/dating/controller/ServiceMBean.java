package service.dating.controller;

import service.dating.model.User;

import java.util.List;

/**
 * Интерфейс взаимодействия с основным приложением
 */
public interface ServiceMBean {

    String getLocation(String serviceToken);

    boolean openChat(String serviceToken, String receiverId);

    List<User> getUsers(String serviceToken, String field, String value);

    boolean hasPermission(String serviceToken, String permission);

    boolean askPermission(String serviceToken, String serviceName, String permission);
}
