package http.requests;

import http.RequestImpl;
import model.User;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class AuthorizationReq extends RequestImpl {
    private static final String PATH = "/authorize";

    public Optional<User> getResponse(String name, String pass) {
        try {
            String data = String.format("{ \"name\": \"%s\", \"password\": \"%s\" }",
                    name, pass);
            String response = super.send(data, PATH);
            //TODO: convert response to User;
            return Optional.of(new User("id", name, 20.0));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
