package controller;

import com.google.gson.Gson;
import controller.exceptions.AuthException;
import controller.exceptions.NotFoundException;
import http.Request;
import http.payload.Friend;
import http.payload.Login;
import http.payload.Register;
import http.request.LoginRequest;
import http.request.RegisterRequest;
import http.request.UserRequest;
import http.request.addChatRequest;
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

    private static User user;

    public static User getUser() {
        return user;
    }

    /**
     * Метод для авторизации пользователя
     * @param login             Данные для входа в систему
     * @throws AuthException    Ошибка при попытке авторизироваться
     */
    public void authorize(Login login) throws AuthException {
        Request request = new LoginRequest();
        Optional<String> response = request.send(login);
        response.orElseThrow(() -> new AuthException(AUTHORIZE_EXCEPTION));
        setUserInfo();
    }

    /**
     * Метод для регистрации пользователя
     * @param register          Данные для регистрации
     * @throws AuthException    Ошибка при попытке регисрации
     */
    public void register(Register register) throws AuthException {
        Request request = new RegisterRequest();
        Optional<String> response = request.send(register);
        response.orElseThrow(() -> new AuthException(REGISTRATION_EXCEPTION));
        setUserInfo();
    }

    /**
     * Метод для получения данных о пользователе
     * @throws AuthException    Ошибка при загрузке данных
     */
    private void setUserInfo() throws AuthException {
        Request request = new UserRequest();
        Optional<String> response = request.send(null);
        if (response.isEmpty()) {
            throw new AuthException(GET_USER_EXCEPTION);
        }
        user = gson.fromJson(response.get(), User.class);
    }

    public User getFriendInfo(Friend friend) throws NotFoundException {
        Request request = new addChatRequest();
        Optional<String> response = request.send(friend);
        if (response.isEmpty()) {
            throw new NotFoundException(GET_FRIEND_EXCEPTION);
        }
        return gson.fromJson(response.get(), User.class);
    }
}
