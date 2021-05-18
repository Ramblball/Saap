package service.weather;

public interface WeatherParser {
    /**
     * Интерфейс для получения готового прогноза погоды
     * @param city
     * @return Готовый прогноз погоды
     */
    String getReadyForecast(String city);
}
