package http.requests;

import com.google.gson.JsonObject;
import http.Request;
import http.RequestImpl;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * Класс для запроса на авторизацию
 */
public class AuthorizationRequest extends RequestImpl implements Request {
    private static final String PATH = "/authorize";

    public Optional<String> send(JsonObject object) {
        try {
            HttpResponse<String> response = makeRequest(object.toString(), PATH);
            if (response.statusCode() == 200) {
                return Optional.of(response.body());
            }
            //TODO: Logger
            System.out.println(response.statusCode() + " -> " + response.body());
            return Optional.empty();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
