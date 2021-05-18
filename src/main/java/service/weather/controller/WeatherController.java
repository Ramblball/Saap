package service.weather.controller;

import lombok.extern.slf4j.Slf4j;
import service.weather.api.APIOpenWeather;
import service.weather.api.WeatherParser;
import service.weather.exception.JMXException;

import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class WeatherController {

    private static final String TOKEN = "60a2419576789c0321851e41";
    private static final String NAME = "Weather";
    private static final String LOCATION_PERMISSION = "LOCATION";

    private static final WeatherParser apiOpenWeather = new APIOpenWeather();

    private JMXController jmxController;

    public String getUserWeather() {
        try {
            jmxController = JMXController.getInstance();
            if (hasPermission() || askPermission()) {
                return getWeather(getLocation().orElseThrow(JMXException::new));
            }
        } catch (IOException | MalformedObjectNameException | JMXException e) {
            log.error(e.getMessage(),e);
        }
        return "";
    }

    private Optional<String> getLocation() {
        String city = jmxController.getBean().getLocation(TOKEN);
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(city);
    }

    private boolean hasPermission() {
        return jmxController.getBean().hasPermission(TOKEN, LOCATION_PERMISSION);
    }

    private boolean askPermission() {
        return jmxController.getBean().askPermission(TOKEN, NAME, LOCATION_PERMISSION);
    }

    public String getWeather(String city){
        return apiOpenWeather.getReadyForecast(city);
    }
}
