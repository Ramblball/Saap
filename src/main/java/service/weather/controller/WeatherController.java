package service.weather.controller;

import lombok.extern.slf4j.Slf4j;
import service.weather.api.APIOpenWeather;
import service.weather.api.WeatherParser;
import service.weather.exception.JMXException;

import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.util.Optional;

/**
 * Класс контроллер для работы с погодой
 */
@Slf4j
public class WeatherController {

    private static final String TOKEN = "60a2419576789c0321851e41";
    private static final String NAME = "Weather";
    private static final String LOCATION_PERMISSION = "LOCATION";

    private static final WeatherParser apiOpenWeather = new APIOpenWeather();

    private JMXController jmxController;

    /**
     * Метод для получения погоды по геолокации пользователя
     *
     * @return Строка с данными о погоде
     */
    public String getUserWeather() {
        try {
            jmxController = JMXController.getInstance();
            if (hasPermission() || askPermission()) {
                return getWeather(getLocation().orElseThrow(JMXException::new));
            }
        } catch (IOException | MalformedObjectNameException | JMXException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * Метод для получения геолокации пользователя
     *
     * @return Геолокация пользователя
     */
    private Optional<String> getLocation() {
        String city = jmxController.getBean().getLocation(TOKEN);
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(city);
    }

    /**
     * Метод для проверки на наличие прав на геолокацию
     *
     * @return true - Сервис имеет право;
     * false - Сервис не имеет права;
     */
    private boolean hasPermission() {
        return jmxController.getBean().hasPermission(TOKEN, LOCATION_PERMISSION);
    }

    /**
     * Метод для добавления права
     *
     * @return true - Разрешено;
     * false - Отказано;
     */
    private boolean askPermission() {
        return jmxController.getBean().askPermission(TOKEN, NAME, LOCATION_PERMISSION);
    }

    /**
     * Метод для получения погоды в указанном городе
     *
     * @param city Город
     * @return Строка с данными о погоде
     */
    public String getWeather(String city) {
        return apiOpenWeather.getReadyForecast(city);
    }
}
