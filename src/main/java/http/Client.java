package http;

import java.net.http.HttpClient;

public class Client {
    private final HttpClient client;

    public Client() {
        client = HttpClient.newHttpClient();
    }

    public HttpClient getClient() {
        return client;
    }
}
