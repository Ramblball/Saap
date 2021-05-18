package service.weather.controller;

public interface ServiceMBean {

    String getLocation(String serviceToken);

    boolean hasPermission(String serviceToken, String permission);

    boolean askPermission(String serviceToken, String serviceName, String permission);
}
