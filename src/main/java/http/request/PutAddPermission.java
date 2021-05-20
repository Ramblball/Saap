package http.request;

import http.AbstractRequest;
import http.HttpLiterals;
import http.Dto;
import http.Request;
import http.dto.PermissionDto;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.util.Optional;

@Slf4j
public class PutAddPermission extends AbstractRequest implements Request {

    private static final String PATH = "/permissions/add/%s";

    @Override
    public Optional<String> send(Dto object) {
        PermissionDto payload = (PermissionDto) object;
        Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, payload.getServiceToken())
                .PUT(HttpRequest.BodyPublishers.ofString(""));
        String uri = String.format(PATH, payload.getPermission().toString());
        log.info(uri + " -> PUT -> " + payload);
        return doRequest(request, uri);
    }
}
