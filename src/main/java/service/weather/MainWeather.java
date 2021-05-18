package service.weather;

import lombok.extern.slf4j.Slf4j;
import service.weather.view.Frame;
import service.weather.view.WeatherFrame;

@Slf4j
public class MainWeather {
    public static void main(String[] args) {
        log.info("Weather started");
        Frame frame = new WeatherFrame();
        frame.build();
    }
}