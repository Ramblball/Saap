package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jdi.request.InvalidRequestStateException;
import controller.exceptions.AuthException;
import http.Request;
import http.requests.AuthorizationRequest;
import http.requests.RegistrationRequest;
import model.ModelLiterals;
import model.User;

import java.util.Optional;

public class AuthController {
    private User user;

    public Optional<User> getUser() {
        if (user == null)
            return Optional.empty();
        return Optional.of(user);
    }

    public void authorize(String username, String password) throws AuthException {
        Request request = new AuthorizationRequest();
        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        object.addProperty(ModelLiterals.USERNAME, username);
        object.addProperty(ModelLiterals.PASSWORD, password);
        Optional<String> response = request.send(object);
        response.ifPresent(s -> this.user = gson.fromJson(s, User.class));
        response.orElseThrow(() -> new AuthException(ControllerLiterals.AUTHORIZE_EXCEPTION));
    }

    public void register(String username, String password, Double age) throws AuthException {
        RegistrationRequest request = new RegistrationRequest();
        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        object.addProperty(ModelLiterals.USERNAME, username);
        object.addProperty(ModelLiterals.PASSWORD, password);
        object.addProperty(ModelLiterals.AGE, age);
        Optional<String > response = request.send(object);
        response.ifPresent(s -> this.user = gson.fromJson(s, User.class));
        response.orElseThrow(() -> new AuthException(ControllerLiterals.REGISTRATION_EXCEPTION));
    }

}
