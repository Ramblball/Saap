package http;

import static java.net.http.HttpRequest.Builder;

import com.google.gson.Gson;
import http.payload.Token;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

/**
 * Класс для составления запроса к серверу
 */
@Slf4j
public abstract class HTTPRequest {
    protected final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();
    private static final String URI_LINK = "https://superappserver.herokuapp.com";
    private static String token;

    /**
     * Метод составляющий запрос к серверу
     *
     * @param request Данные для отправки
     * @param path URI путь запроса
     * @return Полученные данные
     */
    public Optional<String> makeRequest(Builder request, String path) {
        try {
            URI link = URI.create(URI_LINK + path);

            request = request
                    .uri(link)
                    .timeout(Duration.ofMinutes(1))
                    .header(HttpLiterals.CONTENT_TYPE, HttpLiterals.APPLICATION_JSON);
            if (token != null) {
                request = request.header("Authorization", token);
            }
            HttpResponse<String> response =
                    client.send(request.build(), HttpResponse.BodyHandlers.ofString());
            log.info(response.statusCode() + " -> " + response.body());
            if (response.statusCode() == 200) {
                return Optional.of(response.body());
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    public static void setToken(Token token) {
        HTTPRequest.token = token.getPrefix() + token.getToken();
    }

    public static String getToken() {
        return token;
    }
}
