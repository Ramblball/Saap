package http.request;

import http.Dto;
import http.Request;
import http.RequestSender;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на получение информации о пользователе
 */
@Slf4j
public class GetUser implements Request<Dto> {

    private static final String PATH = "/user/info";
    private final RequestSender sender;

    public GetUser(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public Optional<String> send(Dto object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .GET();
        log.info(PATH + " -> GET");
        return sender.doRequest(request, PATH);
    }
}
