package view.service.weather;

import controller.UserController;
import model.User;
import view.Frame;

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
        User user;
        APIOpenWeather apiOpenWeather = new APIOpenWeather();
        user = UserController.getUser();
        JOptionPane.showMessageDialog(this, apiOpenWeather.getReadyForecast(user.getCity()));
    }
}
