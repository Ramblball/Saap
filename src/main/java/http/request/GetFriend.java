package http.request;

import http.AbstractHttpRequest;
import http.Request;
import http.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на получение информации о собеседнике
 */
@Slf4j
public class GetFriend extends AbstractHttpRequest implements Request<ServiceDTO.Request.Param> {

    private static final String PATH = "/friend/%s";

    @Override
    public Optional<String> send(ServiceDTO.Request.Param object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .GET();
        log.info(PATH + " -> GET");
        return doRequest(request, String.format(PATH, object.getParam()));
    }
}
