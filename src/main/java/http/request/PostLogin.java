package http.request;

import com.google.gson.Gson;
import http.HttpSender;
import http.Request;
import http.RequestSender;
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
public class PostLogin implements Request<UserDTO.Request.Login> {

    private static final Gson gson = new Gson();
    private static final String PATH = "/auth/login";
    private final RequestSender sender;

    public PostLogin(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public Optional<String> send(UserDTO.Request.Login object) {
        String data = gson.toJson(object);
        Builder request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data));
        log.info(PATH + " -> POST -> " + data);
        Optional<String> response = sender.doRequest(request, PATH);
        if (response.isEmpty()) {
            return Optional.empty();
        }
        HttpSender.setToken(gson.fromJson(response.get(), ServiceDTO.Response.Token.class));
        return Optional.of("");
    }
}
