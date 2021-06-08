package http.request;

import http.AbstractHttpRequest;
import http.Request;
import http.dto.ServiceDTO;
import http.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.util.Optional;

/**
 * Класс запроса на авторизацию пользователя
 */
@Slf4j
public class PostLogin extends AbstractHttpRequest implements Request<UserDTO.Request.Login> {

    private static final String PATH = "/auth/login";

    @Override
    public Optional<String> send(UserDTO.Request.Login object) {
        String data = gson.toJson(object);
        Builder request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data));
        log.info(PATH + " -> POST -> " + data);
        Optional<String> response = doRequest(request, PATH);
        if (response.isEmpty()) {
            return Optional.empty();
        }
        setToken(gson.fromJson(response.get(), ServiceDTO.Response.Token.class));
        return Optional.of("");
    }
}
