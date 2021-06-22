package service.dating.controller;

/**
 * Интерфейс взаимодействия с основным приложением
 */
public interface ServiceMBean {

    String getLocation(String serviceToken);

    boolean openChat(String serviceToken, String receiverId);

    String getUsers(String serviceToken, String field, String value);

    boolean hasPermission(String serviceToken, String permission);

    boolean askPermission(String serviceToken, String serviceName, String permission);
}
