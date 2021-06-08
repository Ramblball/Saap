package controller;

import com.google.gson.Gson;
import controller.exceptions.AuthException;
import controller.exceptions.NotFoundException;
import http.RequestFactory;
import http.dto.ServiceDTO;
import http.dto.UserDTO;
import model.User;

/**
 * Класс контроллер для аутентификации
 */
public class UserController {

    private static final String AUTHORIZE_EXCEPTION = "Ошибка авторизации";
    private static final String REGISTRATION_EXCEPTION = "Ошибка регистрации";
    private static final String GET_USER_EXCEPTION = "Невозможно загрузить данные пользователя";
    private static final String GET_FRIEND_EXCEPTION = "Пользователь с таким именем не найден";

    private static final Gson gson = new Gson();

    /**
     * Метод для авторизации пользователя
     *
     * @param login Логин пользователя
     * @param password Пароль
     * @throws AuthException Ошибка при попытке авторизироваться
     */
    public User authorize(String login, String password) throws AuthException {
        RequestFactory
                .POST_LOGIN.getRequest()
                .send(new UserDTO.Request.Login(login, password))
                .orElseThrow(() -> new AuthException(AUTHORIZE_EXCEPTION));
        return setUserInfo();
    }

    /**
     * Метод для регистрации пользователя
     *
     * @param name Имя пользователя
     * @param password Пароль
     * @param age Возраст пользователя
     * @param city Место проживания
     * @throws AuthException Ошибка при попытке регисрации
     */
    public User register(String name, String password, Integer age, String city) throws AuthException {
        RequestFactory
                .POST_REGISTER.getRequest()
                .send(new UserDTO.Request.Register(name, password, city, age))
                .orElseThrow(() -> new AuthException(REGISTRATION_EXCEPTION));
        return setUserInfo();
    }

    /**
     * Метод для получения данных о пользователе
     *
     * @throws AuthException Ошибка при загрузке данных
     */
    private User setUserInfo() throws AuthException {
        return gson.fromJson(
                RequestFactory
                        .GET_USER.getRequest()
                        .send(null)
                        .orElseThrow(() -> new AuthException(GET_USER_EXCEPTION)),
                User.class);
    }

    /**
     * Метод для получения данных о собеседнике
     *
     * @param friend DTO объект с именем собеседника
     * @return Объект пользователя-собеседника
     * @throws NotFoundException Не удалось найти пользователя
     */
    public User getFriendInfo(ServiceDTO.Request.Param friend) throws NotFoundException {
        return gson.fromJson(
                RequestFactory
                        .GET_FRIEND.getRequest()
                        .send(friend)
                        .orElseThrow(() -> new NotFoundException(GET_FRIEND_EXCEPTION)),
                User.class);
    }
}
