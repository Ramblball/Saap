package view.service.weather;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainWeather {
    public static void main() {
        log.info("Weather started");
        Frame frame = new WeatherFrame();
        frame.build();
    }
}
