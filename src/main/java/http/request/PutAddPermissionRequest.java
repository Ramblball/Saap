package http.request;

import http.HTTPRequest;
import http.PayLoad;
import http.Request;
import http.payload.PermissionReq;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.util.Optional;

@Slf4j
public class PutAddPermissionRequest extends HTTPRequest implements Request {

    private static final String PATH = "/permissions/add/%s";
    private static final String SERVICE_HEADER = "service";

    @Override
    public Optional<String> send(PayLoad object) {
        PermissionReq payload = (PermissionReq) object;
        Builder request = HttpRequest.newBuilder()
                .header(SERVICE_HEADER, payload.getServiceToken())
                .PUT(HttpRequest.BodyPublishers.ofString(""));
        String uri = String.format(PATH, payload.getPermission().toString());
        log.info(uri + " -> PUT -> " + payload);
        return makeRequest(request, uri);
    }
}
