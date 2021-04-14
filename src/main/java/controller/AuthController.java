package controller;

import model.User;

import java.util.Optional;

public class AuthController {
    private User user;

    public Optional<User> getUser() {
        if (user == null)
            return Optional.empty();
        return Optional.of(user);
    }

    public void authorize(String id, String username) {
    }

    public void register(String id, String username, Double age) {

    }

}
