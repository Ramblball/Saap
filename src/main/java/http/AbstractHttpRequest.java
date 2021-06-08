package http;

import com.google.gson.Gson;
import http.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import static java.net.http.HttpRequest.Builder;
import static java.net.http.HttpResponse.BodyHandlers;

/**
 * Класс для составления запроса к серверу
 */
@Slf4j
public abstract class AbstractHttpRequest {

    // Заголовок указывающий тип данных в теле запроса
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    // Адрес сервера
    private static final String URI_LINK = "https://superappserver.herokuapp.com";

    protected final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();
    private static String token;

    /**
     * Метод для выполнения запроса к серверу
     *
     * @param request Данные для отправки
     * @param path    URI путь запроса
     * @return Полученные данные
     */
    protected Optional<String> doRequest(Builder request, String path) {
        try {
            HttpResponse<String> response =
                    client.send(buildRequest(request, path), BodyHandlers.ofString());
            log.info(response.statusCode() + " -> " + response.body());
            if (response.statusCode() == 200) {
                return Optional.of(response.body());
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Метод для сбокри объекта запроса
     *
     * @param request Сборщик запроса
     * @param path    url путь запроса
     * @return Собранный запрос
     */
    private HttpRequest buildRequest(Builder request, String path) {
        request = request
                .uri(URI.create(URI_LINK + path))
                .timeout(Duration.ofMinutes(1))
                .header(CONTENT_TYPE, APPLICATION_JSON);
        if (token != null) {
            request = request.header("Authorization", token);
        }
        return request.build();
    }

    /**
     * Метод для сохранения токена авторизации в классе
     *
     * @param token Токен авторизации
     */
    protected void setToken(ServiceDTO.Response.Token token) {
        AbstractHttpRequest.token = token.getToken();
    }
}
