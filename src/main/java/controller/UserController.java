package controller;

import com.google.gson.Gson;
import controller.exceptions.AuthException;
import controller.exceptions.NotFoundException;
import http.Request;
import http.dto.ParamDto;
import http.dto.LoginDto;
import http.dto.RegisterDto;
import http.request.GetFriend;
import http.request.GetUser;
import http.request.PostLogin;
import http.request.PostRegister;
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
    // Объект пользователя
    private static User user;

    public static User getUser() {
        return user;
    }

    /**
     * Метод для авторизации пользователя
     *
     * @param login Данные для входа в систему
     * @throws AuthException Ошибка при попытке авторизироваться
     */
    public void authorize(LoginDto login) throws AuthException {
        Request request = new PostLogin();
        Optional<String> response = request.send(login);
        response.orElseThrow(() -> new AuthException(AUTHORIZE_EXCEPTION));
        setUserInfo();
    }

    /**
     * Метод для регистрации пользователя
     *
     * @param register Данные для регистрации
     * @throws AuthException Ошибка при попытке регисрации
     */
    public void register(RegisterDto register) throws AuthException {
        Request request = new PostRegister();
        Optional<String> response = request.send(register);
        response.orElseThrow(() -> new AuthException(REGISTRATION_EXCEPTION));
        setUserInfo();
    }

    /**
     * Метод для получения данных о пользователе
     *
     * @throws AuthException Ошибка при загрузке данных
     */
    private void setUserInfo() throws AuthException {
        Request request = new GetUser();
        Optional<String> response = request.send(null);
        user = gson.fromJson(response
                .orElseThrow(() -> new AuthException(GET_USER_EXCEPTION)), User.class);
    }

    /**
     * Метод для получения данных о собеседнике
     *
     * @param friend DTO объект с именем собеседника
     * @return Объект пользователя-собеседника
     * @throws NotFoundException Не удалось найти пользователя
     */
    public User getFriendInfo(ParamDto friend) throws NotFoundException {
        Request request = new GetFriend();
        Optional<String> response = request.send(friend);
        return gson.fromJson(response
                .orElseThrow(() -> new NotFoundException(GET_FRIEND_EXCEPTION)), User.class);
    }
}
