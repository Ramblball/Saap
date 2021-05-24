package controller;

import com.google.gson.Gson;
import controller.exceptions.ServiceException;
import http.Dto;
import http.Request;
import http.dto.CriteriaDto;
import http.dto.ParamDto;
import http.dto.ServiceParamDto;
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

/**
 * Класс контроллер для работы с сервисами
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceController {

    private static final String CONFIRM_TITLE = "Разрешение доступа";

    private static final String GET_PERMISSIONS_EXCEPTION = "Не удалось получить права пользователя";
    private static final String ADD_PERMISSIONS_EXCEPTION = "Не удалось обновить права";
    private static final String GET_USERS_EXCEPTION = "Не удалось получить пользователей";

    private static final Gson gson = new Gson();

    /**
     * Метод для проверки на наличие прав
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @param permission   Право
     * @return true - Сервис имеет право;
     * false - Сервис не имеет права;
     */
    public boolean hasPermission(String serviceToken, String permission) {
        try {
            Set<String> permissions = getUserPermissions(serviceToken);
            return permissions.contains(permission);
        } catch (ServiceException | ClassCastException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Метод для добавления права
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @param serviceName  Название сервиса
     * @param permission   Право
     * @return true - Разрешено;
     * false - Отказано;
     */
    public boolean addPermission(String serviceToken, String serviceName, String permission) {
        try {
            if (askPermission(serviceName, permission) == 0) {
                return false;
            }
            Dto dto = new ServiceParamDto(serviceToken, permission);
            Request request = new PutAddPermission();
            request.send(dto).orElseThrow(() -> new ServiceException(ADD_PERMISSIONS_EXCEPTION));
            return true;
        } catch (ServiceException | IllegalArgumentException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Метод для получения списка пользователей сервиса с параметром
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @param field        Поле ограничения
     * @param value        Значение поля
     * @return Список пользователей
     */
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

    /**
     * Метод для получения множества прав сервиса для пользователя
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @return Множество прав
     * @throws ServiceException   Не удалось получить права пользователя
     * @throws ClassCastException Не удалось преобразовать полученные данные
     */
    private Set<String> getUserPermissions(String serviceToken) throws ServiceException, ClassCastException {
        Dto dto = new ParamDto(serviceToken);
        Request request = new GetPermissions();
        Optional<String> response = request.send(dto);
        return gson.fromJson(
                response.orElseThrow(() -> new ServiceException(GET_PERMISSIONS_EXCEPTION)),
                HashSet.class);
    }

    /**
     * Метод для отображения окна запроса права у пользователя
     *
     * @param serviceName Название сервиса
     * @param permission  Право
     * @return -1 - ошибка; 0 - разрешено; 1 - отказано;
     */
    private int askPermission(String serviceName, String permission) throws IllegalArgumentException {
        return JOptionPane.showConfirmDialog(
                MainFrame.getInstance(),
                String.format(Permission.valueOf(permission).getMessage(), serviceName),
                CONFIRM_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }
}
