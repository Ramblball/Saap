package http.requests;

import http.RequestImpl;

import java.io.IOException;

public class RegistrationReq extends RequestImpl {
    private static final String PATH = "/registration";

    public void getResponse(String name, String pass, Double age) {
        try {
            String data = String.format("{ \"name\": \"%s\", \"password\": \"%s\", \"age\": %.1f }",
                    name, pass, age);
            String response = super.send(data, PATH);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
