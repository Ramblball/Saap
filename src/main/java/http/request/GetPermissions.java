package http.request;

import http.HttpLiterals;
import http.Request;
import http.RequestSender;
import http.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * Класс запроса на получение списка прав, доступных сервису
 */
@Slf4j
public class GetPermissions implements Request<ServiceDTO.Request.Param> {

    private static final String PATH = "/service/permissions";
    private final RequestSender sender;

    public GetPermissions(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public Optional<String> send(ServiceDTO.Request.Param object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, object.getParam())
                .GET();
        log.info(PATH + " -> GET");
        return sender.doRequest(request, PATH);
    }
}
