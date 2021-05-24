package http;

import java.util.Optional;

/**
 * Интерфейс запроса к серверу
 */
public interface Request {

    /**
     * Метод составляющий запрос к серверу
     *
     * @param object Данные для отправки
     * @return Полученные данные
     */
    Optional<String> send(Dto object);
}
