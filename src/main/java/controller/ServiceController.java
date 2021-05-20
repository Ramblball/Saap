package controller;

import com.google.gson.Gson;
import controller.exceptions.ServiceException;
import http.Dto;
import http.Request;
import http.dto.CriteriaDto;
import http.dto.ParamDto;
import http.dto.PermissionDto;
import http.request.GetPermissions;
import http.request.GetServiceUsers;
import http.request.PutAddPermission;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;
import service.Permission;
import view.main.MainFrame;

import javax.swing.*;
import java.util.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceController {

    private static final String CONFIRM_TITLE = "Разрешение доступа";

    private static final String GET_PERMISSIONS_EXCEPTION = "Не удалось получить права пользователя";
    private static final String ADD_PERMISSIONS_EXCEPTION = "Не удалось обновить права";
    private static final String GET_USERS_EXCEPTION = "Не удалось получить пользователей";

    private static final Gson gson = new Gson();

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
            if (askPermission(serviceName, permission) == 0) {
                return false;
            }
            Dto dto = new PermissionDto(serviceToken, permission);
            Request request = new PutAddPermission();
            Optional<String> response = request.send(dto);
            if (response.isEmpty()) {
                throw new ServiceException(ADD_PERMISSIONS_EXCEPTION);
            }
            return true;
        } catch (ServiceException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    public List<User> getUsers(String serviceToken, String field, String value) {
        try {
            Dto dto = new CriteriaDto(serviceToken, field, value);
            Request request = new GetServiceUsers();
            Optional<String> response = request.send(dto);
            return gson.fromJson(
                    response.orElseThrow(() -> new ServiceException(GET_USERS_EXCEPTION)),
                    List.class
            );
        } catch (ServiceException | ClassCastException ex) {
            log.error(ex.getMessage(), ex);
        }
        return new ArrayList<>();
    }

    private Set<String> getUserPermissions(String serviceToken) throws ServiceException, ClassCastException {
        Dto dto = new ParamDto(serviceToken);
        Request request = new GetPermissions();
        Optional<String> response = request.send(dto);
        return gson.fromJson(
                response.orElseThrow(() -> new ServiceException(GET_PERMISSIONS_EXCEPTION)),
                HashSet.class);
    }

    private int askPermission(String serviceName, String permission) {
        return JOptionPane.showConfirmDialog(
                MainFrame.getInstance(),
                String.format(Permission.getPermissions().get(permission), serviceName),
                CONFIRM_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }
}
