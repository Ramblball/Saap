package service;

import controller.ServiceController;
import controller.UserController;

public class Service implements ServiceMBean {

    private static final String LOCATION_PERMISSION = "LOCATION";

    private static final ServiceController serviceController = new ServiceController();

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
}
