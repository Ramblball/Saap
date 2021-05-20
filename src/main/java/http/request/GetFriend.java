package http.request;

import http.AbstractRequest;
import http.Dto;
import http.Request;
import http.dto.ParamDto;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
public class GetFriend extends AbstractRequest implements Request {

    private static final String PATH = "/friend/%s";

    @Override
    public Optional<String> send(Dto object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .GET();
        log.info(PATH + " -> GET");
        return doRequest(request, String.format(PATH, ((ParamDto) object).getValue()));
    }
}
