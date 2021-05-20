package http.request;

import http.AbstractRequest;
import http.Dto;
import http.Request;
import http.dto.ParamDto;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
public class GetPermissions extends AbstractRequest implements Request {

    private static final String PATH = "/service/permissions";
    private static final String SERVICE_HEADER = "service";

    @Override
    public Optional<String> send(Dto object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .header(SERVICE_HEADER, ((ParamDto) object).getValue())
                .GET();
        log.info(PATH + " -> GET");
        return doRequest(request, PATH);
    }
}
