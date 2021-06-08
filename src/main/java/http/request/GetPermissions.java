package http.request;

import http.AbstractHttpRequest;
import http.HttpLiterals;
import http.Request;
import http.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на получение списка прав, доступных сервису
 */
@Slf4j
public class GetPermissions extends AbstractHttpRequest implements Request<ServiceDTO.Request.Param> {

    private static final String PATH = "/service/permissions";

    @Override
    public Optional<String> send(ServiceDTO.Request.Param object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, object.getParam())
                .GET();
        log.info(PATH + " -> GET");
        return doRequest(request, PATH);
    }
}
