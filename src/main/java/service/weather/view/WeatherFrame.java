package service.weather.view;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import service.weather.WeatherLiterals;
import service.weather.controller.WeatherController;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WeatherFrame extends JFrame implements Frame {

    private static final WeatherController weatherController = new WeatherController();

    Container container = getContentPane();
    JTextField cityField = new JTextField();
    JLabel cityLabel = new JLabel(WeatherLiterals.CITY_LABEL);
    JButton weatherFindButton = new JButton(WeatherLiterals.WEATHER_BUTTON);
    JTextArea weatherTextArea = new JTextArea();

    private void setComponentsStyle() {
        container.setLayout(null);
        cityField.setBounds(100, 10, 150, 30);
        cityLabel.setBounds(50, 10, 100, 30);
        weatherFindButton.setBounds(100, 40, 150, 30);
        weatherTextArea.setBounds(300, 10, 300, 400);
        weatherTextArea.setEditable(false);
    }

    private void addComponentsToContainer() {
        container.add(cityLabel);
        container.add(cityField);
        container.add(weatherTextArea);
        container.add(weatherFindButton);
    }

    private void addListeners() {
        weatherFindButton.addActionListener(e ->{
            String cityText = cityField.getText();
            if (cityText.trim().equals("")) {
                JOptionPane.showMessageDialog(this, WeatherLiterals.EMPTY_FIELDS_DIALOG);
            }else{
                weatherTextArea.setText(weatherController.getWeather(cityText));
            }
        });
    }

    @Override
    public void build() {
        weatherTextArea.setText(weatherController.getUserWeather());
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        setResizable(false);
        setVisible(true);
        pack();
    }
}
