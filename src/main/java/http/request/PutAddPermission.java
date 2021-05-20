package http.request;

import http.AbstractRequest;
import http.HttpLiterals;
import http.Dto;
import http.Request;
import http.dto.ServiceParamDto;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.util.Optional;

/**
 * Класс запроса на добавление прав сервису
 */
@Slf4j
public class PutAddPermission extends AbstractRequest implements Request {

    private static final String PATH = "/permissions/add/%s";

    @Override
    public Optional<String> send(Dto object) {
        ServiceParamDto payload = (ServiceParamDto) object;
        Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, payload.getServiceToken())
                .PUT(HttpRequest.BodyPublishers.ofString(""));
        String uri = String.format(PATH, payload.getValue());
        log.info(uri + " -> PUT -> " + payload);
        return doRequest(request, uri);
    }
}
