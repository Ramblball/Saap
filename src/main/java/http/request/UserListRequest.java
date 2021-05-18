package http.request;

import http.HTTPRequest;
import http.PayLoad;
import http.Request;
import http.payload.DatingUser;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class UserListRequest extends HTTPRequest implements Request {

    private static final String PATH = "/user/list/";

    @Override
    public Optional<String> send(PayLoad object) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .GET();
        log.info(PATH + " -> GET");
        return makeRequest(request, PATH + ((DatingUser)object).getCity());
    }
//
//    private String encode(String string){
//        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
//        return new String(bytes, StandardCharsets.UTF_8);
//    }
}
