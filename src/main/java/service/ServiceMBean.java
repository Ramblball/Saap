package service;

import model.User;

import java.util.List;

public interface ServiceMBean {

    String getLocation(String serviceToken);

    boolean openChat(String serviceToken, String receiverName);

    List<User> getUsers(String serviceToken, String field, String value);

    boolean hasPermission(String serviceToken, String permission);

    boolean askPermission(String serviceToken, String serviceName, String permission);
}