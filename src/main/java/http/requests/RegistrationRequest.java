package http.requests;

import com.google.gson.JsonObject;
import http.Request;
import http.RequestImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * Класс для запроса на регистрацию
 */
@Slf4j
public class RegistrationRequest extends RequestImpl implements Request {
    private static final String PATH = "/registration";

    @Override
    public Optional<String> send(JsonObject object) {
        try {
            HttpResponse<String> response = makeRequest(object.toString(), PATH);
            log.debug(response.statusCode() + " -> " + response.body());
            if (response.statusCode() == 200) {
                return Optional.of(response.body());
            }
            return Optional.empty();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
