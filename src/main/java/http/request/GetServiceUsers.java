package http.request;

import http.AbstractHttpRequest;
import http.HttpLiterals;
import http.Request;
import http.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на получение списка пользователей, использующих сервис
 */
@Slf4j
public class GetServiceUsers extends AbstractHttpRequest implements Request<ServiceDTO.Request.Criteria> {

    private static final String PATH = "/service/list/%s/%s";

    @Override
    public Optional<String> send(ServiceDTO.Request.Criteria object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, object.getToken())
                .GET();
        String uri = String.format(PATH, object.getField(), object.getParam());
        log.info(uri + " -> PUT -> " + object);
        return doRequest(request, uri);
    }
}
