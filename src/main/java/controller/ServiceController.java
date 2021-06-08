package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.exceptions.ServiceException;
import http.RequestFactory;
import http.dto.ServiceDTO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import service.Permission;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

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
     * @param token Уникальный идентификатор сервиса
     * @param param Запрашиваемое разрешение
     * @param serviceName Название сервиса
     * @return true - Разрешено;
     * false - Отказано;
     */
    public boolean addPermission(String token, String param, String serviceName) {
        try {
            if (askPermission(serviceName, param) != 0) {
                return false;
            }
            RequestFactory
                    .PUT_ADD_PERMISSION.getRequest()
                    .send(new ServiceDTO.Request.ServiceParam(token, param))
                    .orElseThrow(() -> new ServiceException(ADD_PERMISSIONS_EXCEPTION));
            return true;
        } catch (ServiceException | IllegalArgumentException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Метод для получения списка пользователей сервиса с параметром
     *
     * @param token Уникальный идентификатор сервиса
     * @param field Поле критерия поиска
     * @param param Критерий поиска
     * @return Список пользователей
     */
    public String getUsers(String token, String field, String param) {
        try {
            return RequestFactory
                    .GET_SERVICE_USERS.getRequest()
                    .send(new ServiceDTO.Request.Criteria(token, field, param))
                    .orElseThrow(() -> new ServiceException(GET_USERS_EXCEPTION));
        } catch (ServiceException ex) {
            log.error(ex.getMessage(), ex);
        }
        return "";
    }

    /**
     * Метод для получения множества прав сервиса для пользователя
     *
     * @param token Уникальный идентификатор сервиса
     * @return Множество прав
     * @throws ServiceException   Не удалось получить права пользователя
     * @throws ClassCastException Не удалось преобразовать полученные данные
     */
    private Set<String> getUserPermissions(String token) throws ServiceException, ClassCastException {
        return gson.fromJson(
                RequestFactory
                        .GET_PERMISSIONS.getRequest()
                        .send(new ServiceDTO.Request.Param(token))
                        .orElseThrow(() -> new ServiceException(GET_PERMISSIONS_EXCEPTION)),
                new TypeToken<HashSet<String>>() {
                }.getType()
        );
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
                null,
                String.format(Permission.valueOf(permission).getMessage(), serviceName),
                CONFIRM_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }
}
