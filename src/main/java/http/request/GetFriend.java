package http.request;

import http.RequestSender;
import http.Request;
import http.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на получение информации о собеседнике
 */
@Slf4j
public class GetFriend implements Request<UserDTO.Request.Friend> {

    private static final String PATH = "/friend/%s";
    private final RequestSender sender;

    public GetFriend(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public Optional<String> send(UserDTO.Request.Friend object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .GET();
        log.info(PATH + " -> GET");
        return sender.doRequest(request, String.format(PATH, object.getName()));
    }
}
