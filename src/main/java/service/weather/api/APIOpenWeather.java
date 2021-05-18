package service.weather;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
public class APIOpenWeather implements WeatherParser {
    /**
     * Константа форматирования даты в апи
     */
    private final static DateTimeFormatter INPUT_DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Константа форматирования даты для вывода пользователю
     */
    private final static DateTimeFormatter OUTPUT_DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd (MMMM) в HH:mm :");

    private final static String URL_STRING =
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&APPID=d97a349114c8783aa4c0f7884f74ba63";

    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Получение готового прогноза погоды на 5 дней в заданном городе
     *
     * @param city
     * @return Прогноз на 5 дней в заданном городе
     */
    @Override
    public String getReadyForecast(String city) {
        try {
            Optional<String> jsonRawData = downloadJsonRawData(city);
            //jsonRawData.map(s -> convertRawDataToList(s).get());
            String result = String.format("%s:%s%s", city, System.lineSeparator(),
                    parseForecastDataFromList(convertRawDataToList(jsonRawData.get())).get());
            return result;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return String.format("Не могу найти \"%s\" город.", city);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return "Услуга недоступна, пожалуйста, попробуйте позже";
        }
    }

    /**
     * Скачивание исходных данных в формате JSON
     *
     * @param city
     * @return Ответ с сайта openweathermap.org
     * @throws Exception
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

            if (response.statusCode() == 404) {
                throw new NotFoundException();
            }

            return Optional.of(response.body());
        } catch (NotFoundException | IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Преобразование необработанных данных в список
     *
     * @param data
     * @return Список с данными о погоде
     * @throws Exception
     */
    private Optional<List<String>> convertRawDataToList(String data) {
        List<String> weatherList = new ArrayList<>();

        try {
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
     * @param weatherList
     * @return Готовый текстовый формат выводимой информации
     * @throws Exception
     */
    private Optional<String> parseForecastDataFromList(Optional<List<String>> weatherList){
        final StringBuffer sb = new StringBuffer();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (String line : weatherList.get()) {
                    String dateTime;
                    JsonNode mainNode;
                    JsonNode weatherArrNode;
                        mainNode = objectMapper.readTree(line).get("main");
                        weatherArrNode = objectMapper.readTree(line).get("weather");
                        for (final JsonNode objNode : weatherArrNode) {
                            dateTime = objectMapper.readTree(line).get("dt_txt").toString();
                            sb.append(formatForecastData(dateTime, objNode.get("main").toString(),
                                    mainNode.get("temp").asDouble()));
                        }
            }
            return Optional.of(sb.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Формат выводимой информации о погоде
     *
     * @param dateTime
     * @param description
     * @param temperature
     * @return Строчки с данными о дне, месяце, времени, температуры, состояния погоды
     * @throws Exception
     */
    private static String formatForecastData(String dateTime, String description, double temperature){
        LocalDateTime forecastDateTime = LocalDateTime.parse(dateTime.replaceAll("\"", ""), INPUT_DATE_TIME_FORMAT);
        String formattedDateTime = forecastDateTime.format(OUTPUT_DATE_TIME_FORMAT);

        String formattedTemperature;
        long roundedTemperature = Math.round(temperature);
        if (roundedTemperature > 0) {
            formattedTemperature = "+" + Math.round(temperature);
        } else {
            formattedTemperature = String.valueOf(Math.round(temperature));
        }

        String formattedDescription = description.replaceAll("\"", "");

        String weatherIconCode = WeatherUtils.weatherIconsCodes.get(formattedDescription);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(formattedDateTime).append(formattedTemperature)
                .append(formattedDescription).append(weatherIconCode).append(System.lineSeparator());
        return stringBuilder.toString();
    }
}

