package http.requests;

import http.RequestImpl;

import java.io.IOException;
import java.util.Map;

public class AuthorizationReq extends RequestImpl {
    private static final String PATH = "/authorize";

    public void getResponse(String name, String pass) {
        try {
            String data = String.format("{ \"name\": \"%s\", \"password\": \"%s\" }",
                    name, pass);
            String response = super.send(data, PATH);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
