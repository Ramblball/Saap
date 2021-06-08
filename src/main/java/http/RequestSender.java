package http;

import java.net.http.HttpRequest;
import java.util.Optional;

public interface RequestSender {

    Optional<String> doRequest(HttpRequest.Builder request, String path);
}
