package view.service.weather;

import controller.UserController;
import model.User;

import javax.swing.*;

public class WeatherFrame extends JFrame implements Frame {
    @Override
    public void setComponentsStyle() {

    }

    @Override
    public void addComponentsToContainer() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void build() {
        openWeather();
    }

    private void openWeather(){
        WeatherParser apiOpenWeather = new APIOpenWeather();
        User user = UserController.getUser();
        JOptionPane.showMessageDialog(this, apiOpenWeather.getReadyForecast(user.getCity()));
    }
}
