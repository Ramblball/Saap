package http.request;

import http.HTTPRequest;
import http.PayLoad;
import http.Request;
import http.payload.FieldReq;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
public class GetPermissionsRequest extends HTTPRequest implements Request {

    private static final String PATH = "/service/permissions";
    private static final String SERVICE_HEADER = "service";

    @Override
    public Optional<String> send(PayLoad object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .header(SERVICE_HEADER, ((FieldReq) object).getValue())
                .GET();
        log.info(PATH + " -> GET");
        return makeRequest(request, PATH);
    }
}
