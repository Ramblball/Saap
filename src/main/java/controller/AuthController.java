package controller;

import http.requests.AuthorizationReq;
import http.requests.RegistrationReq;
import model.User;

import java.util.Optional;

public class AuthController {
    private User user;

    public Optional<User> getUser() {
        if (user == null)
            return Optional.empty();
        return Optional.of(user);
    }

    public void authorize(String username, String password) {
        AuthorizationReq request = new AuthorizationReq();
        Optional<User> response = request.getResponse(username, password);
        user = response.orElseThrow();
    }

    public void register(String username, String password, Double age) {
        RegistrationReq request = new RegistrationReq();
        Optional<User> response = request.getResponse(username, password, age);
        user = response.orElseThrow();
    }

}
