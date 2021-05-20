package http.request;

import http.AbstractRequest;
import http.Dto;
import http.Request;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
public class GetUser extends AbstractRequest implements Request {

    private static final String PATH = "/user/info";

    @Override
    public Optional<String> send(Dto object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .GET();
        log.info(PATH + " -> GET");
        return doRequest(request, PATH);
    }
}
