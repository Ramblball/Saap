package service;

public interface ServiceMBean {

    String getLocation(String serviceToken);

    boolean askPermission(String serviceToken, String serviceName, String permission);
}
