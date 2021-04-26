package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class RequestImpl {
    private final Client client = new Client();

    protected HttpResponse<String> send(String data, String path) throws IOException, InterruptedException {
        URI link = URI.create(HTTPLiterals.URI_LINK + path);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(link)
                .timeout(Duration.ofMinutes(1))
                .header(HTTPLiterals.CONTENT_TYPE, HTTPLiterals.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofByteArray(data.getBytes()))
                .build();
        return client.getClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
