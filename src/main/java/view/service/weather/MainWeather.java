package view.service.weather;

import lombok.extern.slf4j.Slf4j;
import view.auth.LoginFrame;

import javax.swing.*;
@Slf4j
public class MainWeather {
    public static void main() {
        log.info("Weather started");
        WeatherFrame frame = new WeatherFrame();
        frame.build();
    }
}
