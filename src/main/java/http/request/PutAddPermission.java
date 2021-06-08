package http.request;

import http.HttpLiterals;
import http.Request;
import http.RequestSender;
import http.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.util.Optional;

/**
 * Класс запроса на добавление прав сервису
 */
@Slf4j
public class PutAddPermission implements Request<ServiceDTO.Request.ServiceParam> {

    private static final String PATH = "/service/permissions/add/%s";
    private final RequestSender sender;

    public PutAddPermission(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public Optional<String> send(ServiceDTO.Request.ServiceParam object) {
        Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, object.getToken())
                .PUT(HttpRequest.BodyPublishers.ofString("{}"));
        String uri = String.format(PATH, object.getParam());
        log.info(uri + " -> PUT -> " + object);
        return sender.doRequest(request, uri);
    }
}
