package http.request;

import http.AbstractRequest;
import http.Request;
import http.Dto;
import http.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest.Builder;
import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на регистрацию пользователя
 */
@Slf4j
public class PostRegister extends AbstractRequest implements Request {
    private static final String PATH = "/auth/registration";

    @Override
    public Optional<String> send(Dto object) {
        String data = gson.toJson(object);
        Builder request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data));
        log.info(PATH + " -> POST -> " + data);
        Optional<String> response = doRequest(request, PATH);
        if (response.isEmpty()) {
            return Optional.empty();
        }
        TokenDto token = gson.fromJson(response.get(), TokenDto.class);
        setToken(token);
        return Optional.of("");
    }
}
