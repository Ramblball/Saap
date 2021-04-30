package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Класс для составления запроса к серверу
 */
public abstract class RequestImpl {
    private final Client client = new Client();
    public static final String URI_LINK = "https://superappserver.herokuapp.com";

    /**
     * Метод составляющий запрос к серверу
     * @param data                      Данные для отправки
     * @param path                      URI путь запроса
     * @return                          Полученные данные
     * @throws IOException              Ошибка при работе с сокет соединением
     * @throws InterruptedException     Ошибка экстренного завершения потока
     */
    protected HttpResponse<String> makeRequest(String data, String path) throws IOException, InterruptedException {
        URI link = URI.create(URI_LINK + path);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(link)
                .timeout(Duration.ofMinutes(1))
                .header(HTTPLiterals.CONTENT_TYPE, HTTPLiterals.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofByteArray(data.getBytes()))
                .build();
        return client.getClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
