package controller;

import com.google.gson.Gson;
import controller.exceptions.AuthException;
import controller.exceptions.NotFoundException;
import http.Dto;
import http.Request;
import http.dto.ParamDto;
import http.dto.LoginDto;
import http.dto.RegisterDto;
import http.request.GetUser;
import model.User;

import java.util.Optional;

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
     * @param login Данные для входа в систему
     * @throws AuthException Ошибка при попытке авторизироваться
     */
    public User authorize(Request request, Dto login) throws AuthException {
        Optional<String> response = request.send(login);
        response.orElseThrow(() -> new AuthException(AUTHORIZE_EXCEPTION));
        return setUserInfo(new GetUser());
    }

    /**
     * Метод для регистрации пользователя
     *
     * @param register Данные для регистрации
     * @throws AuthException Ошибка при попытке регисрации
     */
    public User register(Request request, Dto register) throws AuthException {
        Optional<String> response = request.send(register);
        response.orElseThrow(() -> new AuthException(REGISTRATION_EXCEPTION));
        return setUserInfo(new GetUser());
    }

    /**
     * Метод для получения данных о пользователе
     *
     * @throws AuthException Ошибка при загрузке данных
     */
    private User setUserInfo(Request request) throws AuthException {
        Optional<String> response = request.send(null);
        return gson.fromJson(response
                .orElseThrow(() -> new AuthException(GET_USER_EXCEPTION)), User.class);
    }

    /**
     * Метод для получения данных о собеседнике
     *
     * @param friend DTO объект с именем собеседника
     * @return Объект пользователя-собеседника
     * @throws NotFoundException Не удалось найти пользователя
     */
    public User getFriendInfo(Request request, Dto friend) throws NotFoundException {
        Optional<String> response = request.send(friend);
        return gson.fromJson(response
                .orElseThrow(() -> new NotFoundException(GET_FRIEND_EXCEPTION)), User.class);
    }
}
