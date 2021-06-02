package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import service.Permission;

import javax.swing.*;
import java.util.HashSet;
import java.util.Optional;
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
            Set<String> permissions = getUserPermissions(new GetPermissions(), new ParamDto(serviceToken));
            return permissions.contains(permission);
        } catch (ServiceException | ClassCastException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Метод для добавления права
     *
     * @param serviceName  Название сервиса
     * @return true - Разрешено;
     * false - Отказано;
     */
    public boolean addPermission(Request request, ServiceParamDto dto, String serviceName) {
        try {
            if (askPermission(serviceName, dto.getValue()) != 0) {
                return false;
            }
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
     * @return Список пользователей
     */
    public String getUsers(Request request, Dto dto) {
        try {
            Optional<String> response = request.send(dto);
            return response.orElseThrow(() -> new ServiceException(GET_USERS_EXCEPTION));
        } catch (ServiceException | ClassCastException ex) {
            log.error(ex.getMessage(), ex);
        }
        return "";
    }

    /**
     * Метод для получения множества прав сервиса для пользователя
     *
     * @return Множество прав
     * @throws ServiceException   Не удалось получить права пользователя
     * @throws ClassCastException Не удалось преобразовать полученные данные
     */
    private Set<String> getUserPermissions(Request request, Dto dto) throws ServiceException, ClassCastException {
        Optional<String> response = request.send(dto);
        return gson.fromJson(
                response.orElseThrow(() -> new ServiceException(GET_PERMISSIONS_EXCEPTION)),
                new TypeToken<HashSet<String>>(){}.getType()
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
