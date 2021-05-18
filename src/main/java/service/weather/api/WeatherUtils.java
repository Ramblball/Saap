package service.weather.api;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {
    public final static Map<String, String> weatherIconsCodes = new HashMap<>();

    /*
      Конструктор для состояния погоды
     */
    static {
        weatherIconsCodes.put("Clear", "☀");
        weatherIconsCodes.put("Rain", "☔");
        weatherIconsCodes.put("Snow", "❄");
        weatherIconsCodes.put("Clouds", "☁");
    }
}
