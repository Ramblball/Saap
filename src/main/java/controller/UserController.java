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
        response.orElseThrow(() -> new AuthException(ControllerLiterals.AUTHORIZE_EXCEPTION));
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
        response.orElseThrow(() -> new AuthException(ControllerLiterals.REGISTRATION_EXCEPTION));
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
            throw new AuthException("Невозможно загрузить данные пользователя");
        }
        user = gson.fromJson(response.get(), User.class);
    }
}
