package http.request;

import http.AbstractRequest;
import http.HttpLiterals;
import http.Dto;
import http.Request;
import http.dto.CriteriaDto;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на получение списка пользователей, использующих сервис
 */
@Slf4j
public class GetServiceUsers extends AbstractRequest implements Request {

    private static final String PATH = "/service/list/%s/%s";

    @Override
    public Optional<String> send(Dto object) {
        CriteriaDto payload = (CriteriaDto) object;
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, payload.getServiceToken())
                .GET();
        String uri = String.format(PATH, payload.getCriteria(), payload.getValue());
        log.info(uri + " -> PUT -> " + payload);
        return doRequest(request, uri);
    }
}
