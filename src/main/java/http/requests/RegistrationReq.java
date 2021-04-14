package http.requests;

import http.RequestImpl;
import model.Entity;
import model.User;

import java.io.IOException;
import java.util.Optional;

public class RegistrationReq extends RequestImpl {
    private static final String PATH = "/registration";

    public Optional<User> getResponse(String name, String pass, Double age) {
        try {
            String data = String.format("{ \"name\": \"%s\", \"password\": \"%s\", \"age\": %.1f }",
                    name, pass, age);
            String response = super.send(data, PATH);
            //TODO: convert response to User;
            return Optional.of(new User("id", name, age));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
