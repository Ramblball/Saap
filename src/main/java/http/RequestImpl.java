package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class RequestImpl implements Request{
    private static final String URI_LINK = "https://superappserver.herokuapp.com";
    private final Client client = new Client();

    @Override
    public String send(String data, String path) throws IOException, InterruptedException {
        URI link = URI.create(URI_LINK + path);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(link)
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
        HttpResponse<String> response = client.getClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
