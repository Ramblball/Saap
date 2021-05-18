package controller;

import com.google.gson.Gson;
import controller.exceptions.ServiceException;
import http.PayLoad;
import http.Request;
import http.payload.FieldReq;
import http.payload.PermissionReq;
import http.request.GetPermissionsRequest;
import http.request.PutAddPermissionRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import jmxapi.Permission;
import view.main.MainFrame;

import javax.swing.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceController {

    private static final String CONFIRM_TITLE = "Разрешение доступа";

    private static final String GET_PERMISSIONS_EXCEPTION = "Не удалось получить права пользователя";
    private static final String ADD_PERMISSIONS_EXCEPTION = "Не удалось обновить права";

    private static final Gson gson = new Gson();

    private Set<String> getUserPermissions(String serviceToken) throws ServiceException, ClassCastException {
        PayLoad payLoad = new FieldReq(serviceToken);
        Request request = new GetPermissionsRequest();
        Optional<String> response = request.send(payLoad);
        return gson.fromJson(
                response.orElseThrow(() -> new ServiceException(GET_PERMISSIONS_EXCEPTION)),
                HashSet.class);
    }

    public boolean hasPermission(String serviceToken, String permission) {
        try {
            Set<String> permissions = getUserPermissions(serviceToken);
            return permissions.contains(permission);
        } catch (ServiceException | ClassCastException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    public boolean addPermission(String serviceToken, String serviceName, String permission) {
        try {
            if (!Permission.getPermissions().containsKey(permission)) {
                return false;
            }
            int res = JOptionPane.showConfirmDialog(
                    MainFrame.buildInstance(),
                    String.format(Permission.getPermissions().get(permission), serviceName),
                    CONFIRM_TITLE,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (res == 0) {
                return false;
            }
            PayLoad payLoad = new PermissionReq(serviceToken, permission);
            Request request = new PutAddPermissionRequest();
            Optional<String> response = request.send(payLoad);
            if (response.isEmpty()) {
                throw new ServiceException(ADD_PERMISSIONS_EXCEPTION);
            }
            return true;
        } catch (ServiceException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }
}
