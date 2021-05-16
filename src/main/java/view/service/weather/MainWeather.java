package view.service.weather;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainWeather {
    public static void main(String[] args) {
        log.info("Weather started");
        Frame frame = new WeatherFrame();
        if (args.length == 0)
            args = new String[1];
        frame.build(args[0]);
    }
}
