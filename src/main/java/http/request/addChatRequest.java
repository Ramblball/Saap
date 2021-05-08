package http.request;

import http.HTTPRequest;
import http.PayLoad;
import http.Request;
import http.payload.Friend;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
public class addChatRequest extends HTTPRequest implements Request {

    private static final String PATH = "/friend/";

    @Override
    public Optional<String> send(PayLoad object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .GET();
        log.info(PATH + " -> GET");
        return makeRequest(request, PATH + ((Friend) object).getName());
    }
}
