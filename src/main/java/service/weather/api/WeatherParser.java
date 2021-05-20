package service.weather.api;

public interface WeatherParser {
    /**
     * Получение готового прогноза погоды на 5 дней в заданном городе
     *
     * @param city Город для поиска
     * @return Прогноз на 5 дней в заданном городе
     */
    String getReadyForecast(String city);
}
