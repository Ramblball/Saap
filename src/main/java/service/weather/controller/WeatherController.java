package service.weather.controller;

import controller.UserController;
import model.User;
import service.weather.api.APIOpenWeather;
import service.weather.api.WeatherParser;

public class WeatherContoller {

    private final User city = UserController.getUser();
    private final WeatherParser apiOpenWeather = new APIOpenWeather();

    public String getUserWeather() {
        String weather = null;
        if(city != null){
            weather = getWeather(city.getCity());
        }
        return weather;
    }

    public String getWeather(String city){
        String weather = apiOpenWeather.getReadyForecast(city);
        return weather;
    }

    private static final String token = "60a2419576789c0321851e41";
}
