package service.weather.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import service.weather.exception.NotFoundException;
import service.weather.exception.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для преобразования данных о погоде
 */
@Slf4j
public class APIOpenWeather implements WeatherParser {

    // Константа форматирования даты в апи
    private final static DateTimeFormatter INPUT_DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Константа форматирования даты для вывода пользователю
    private final static DateTimeFormatter OUTPUT_DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd (MMMM) в HH:mm :");

    private final static String URL_STRING =
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&APPID=d97a349114c8783aa4c0f7884f74ba63";

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public String getReadyForecast(String city) {
        try {
            return String.format("%s:%s%s", city, System.lineSeparator(),
                    parseForecastDataFromList(
                            convertRawDataToList(
                                    downloadJsonRawData(city)
                                            .orElseThrow(NotFoundException::new))
                                    .orElseThrow(ParseException::new))
                            .orElseThrow(ParseException::new));
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
            return "Не удалось найти указанный город";
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return "Услуга недоступна, пожалуйста, попробуйте позже";
        }
    }

    /**
     * Скачивание исходных данных в формате JSON
     *
     * @param city Город для поиска
     * @return Ответ сервера
     */
    private Optional<String> downloadJsonRawData(String city) {
        try {
            URI uriObject = URI.create(String.format(URL_STRING, city));
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(uriObject)
                    .timeout(Duration.ofMinutes(1))
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return Optional.of(response.body());
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Преобразование необработанных данных в список
     *
     * @param data данные для обработки
     * @return Список с данными о погоде
     */
    private Optional<List<String>> convertRawDataToList(String data) {
        try {
            List<String> weatherList = new ArrayList<>();
            JsonNode arrNode = new ObjectMapper().readTree(data).get("list");
            if (arrNode.isArray()) {
                for (final JsonNode objNode : arrNode) {
                    String forecastTime = objNode.get("dt_txt").toString();
                    if (forecastTime.contains("06:00") || forecastTime.contains("12:00")
                            || forecastTime.contains("18:00")) {
                        weatherList.add(objNode.toString());
                    }
                }
            }
            return Optional.of(weatherList);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Разбор данных о прогнозе погоды из списка с данными о погоде
     *
     * @param weatherList Список с данными
     * @return Готовый текстовый формат выводимой информации
     */
    private Optional<String> parseForecastDataFromList(List<String> weatherList) {
        StringBuilder builder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (String line : weatherList) {
                JsonNode mainNode = objectMapper.readTree(line).get("main");
                JsonNode weatherArrNode = objectMapper.readTree(line).get("weather");
                for (final JsonNode objNode : weatherArrNode) {
                    String dateTime = objectMapper.readTree(line).get("dt_txt").toString();
                    builder.append(formatForecastData(dateTime, objNode.get("main").toString(),
                            mainNode.get("temp").asDouble()));
                }
            }
            return Optional.of(builder.toString());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Формат выводимой информации о погоде
     *
     * @param dateTime    Время
     * @param description Описание
     * @param temperature Температура
     * @return Строчки с данными о дне, месяце, времени, температуры, состояния погоды
     */
    private static String formatForecastData(String dateTime, String description, double temperature) {
        LocalDateTime forecastDateTime = LocalDateTime.parse(dateTime.replaceAll("\"", ""), INPUT_DATE_TIME_FORMAT);
        String formattedDateTime = forecastDateTime.format(OUTPUT_DATE_TIME_FORMAT);

        long roundedTemperature = Math.round(temperature);
        String formattedTemperature = roundedTemperature > 0
                ? "+" + roundedTemperature
                : String.valueOf(roundedTemperature);

        String formattedDescription = description.replaceAll("\"", "");

        String weatherIconCode = WeatherUtils.weatherIconsCodes.get(formattedDescription);

        return formattedDateTime + formattedTemperature +
                formattedDescription + weatherIconCode + System.lineSeparator();
    }
}

