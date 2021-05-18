package controller;

import com.google.gson.Gson;
import controller.exceptions.AuthException;
import controller.exceptions.NotFoundException;
import http.Request;
import http.payload.FieldReq;
import http.payload.LoginReq;
import http.payload.RegisterReq;
import http.request.PostLoginRequest;
import http.request.PostRegisterRequest;
import http.request.GetUserRequest;
import http.request.GetFriendInfoRequest;
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
    public void authorize(LoginReq login) throws AuthException {
        Request request = new PostLoginRequest();
        Optional<String> response = request.send(login);
        response.orElseThrow(() -> new AuthException(AUTHORIZE_EXCEPTION));
        setUserInfo();
    }

    /**
     * Метод для регистрации пользователя
     * @param register          Данные для регистрации
     * @throws AuthException    Ошибка при попытке регисрации
     */
    public void register(RegisterReq register) throws AuthException {
        Request request = new PostRegisterRequest();
        Optional<String> response = request.send(register);
        response.orElseThrow(() -> new AuthException(REGISTRATION_EXCEPTION));
        setUserInfo();
    }

    /**
     * Метод для получения данных о пользователе
     * @throws AuthException    Ошибка при загрузке данных
     */
    private void setUserInfo() throws AuthException {
        Request request = new GetUserRequest();
        Optional<String> response = request.send(null);
        if (response.isEmpty()) {
            throw new AuthException(GET_USER_EXCEPTION);
        }
        user = gson.fromJson(response.get(), User.class);
    }

    public User getFriendInfo(FieldReq friend) throws NotFoundException {
        Request request = new GetFriendInfoRequest();
        Optional<String> response = request.send(friend);
        if (response.isEmpty()) {
            throw new NotFoundException(GET_FRIEND_EXCEPTION);
        }
        return gson.fromJson(response.get(), User.class);
    }
}
