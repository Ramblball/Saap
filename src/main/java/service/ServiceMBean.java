package service;

import model.User;

import java.util.List;

/**
 * Интерфейс MBean для обращения сервисов по jmx
 */
public interface ServiceMBean {

    /**
     * Метод для получения геолокации пользователя
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @return Местоположение пользователя
     */
    String getLocation(String serviceToken);

    /**
     * Метод для открытия чата
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @param receiverName Имя собеседника
     * @return true - Сервис имеет право;
     * false - Сервис не имеет права;
     */
    boolean openChat(String serviceToken, String receiverName);

    /**
     * Метод для получения списка пользователей сервиса с параметром
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @param field        Поле ограничения
     * @param value        Значение поля
     * @return Список пользоваателей
     */
    List<User> getUsers(String serviceToken, String field, String value);

    /**
     * Метод для проверки на наличие прав
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @param permission   Право
     * @return true - Сервис имеет право;
     * false - Сервис не имеет права;
     */
    boolean hasPermission(String serviceToken, String permission);

    /**
     * Метод для добавления права
     *
     * @param serviceToken Уникальный идентификатор сервиса
     * @param serviceName  Название сервиса
     * @param permission   Право
     * @return true - Разрешено;
     * false - Отказано;
     */
    boolean askPermission(String serviceToken, String serviceName, String permission);
}