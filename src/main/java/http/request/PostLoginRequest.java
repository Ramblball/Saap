package http.request;

import http.HTTPRequest;
import http.Request;
import http.PayLoad;
import http.payload.TokenRes;
import lombok.extern.slf4j.Slf4j;


import java.net.http.HttpRequest.Builder;
import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
public class PostLoginRequest extends HTTPRequest implements Request {

    private static final String PATH = "/auth/login";

    @Override
    public Optional<String> send(PayLoad object) {
        String data = gson.toJson(object);
        Builder request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data));
        log.info(PATH + " -> POST -> " + data);
        Optional<String> response = makeRequest(request, PATH);
        if (response.isEmpty()) {
            return Optional.empty();
        }
        TokenRes token = gson.fromJson(response.get(), TokenRes.class);
        setToken(token);
        return Optional.of("");
    }
}
