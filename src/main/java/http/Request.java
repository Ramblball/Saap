package http;

import com.google.gson.JsonObject;

import java.util.Optional;

/**
 * Интерфейс запроса к серверу
 */
public interface Request {
    /**
     * Метод отправляющий запрос на сервер
     * @param object        Данные для отправки
     * @return              Данные полученные от сервера
     */
    Optional<String> send(JsonObject object);
}
