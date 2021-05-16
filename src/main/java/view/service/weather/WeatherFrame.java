package view.service.weather;

import controller.UserController;
import lombok.extern.slf4j.Slf4j;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;

@Slf4j
public class WeatherFrame extends JFrame implements Frame {

    private  WeatherParser apiOpenWeather = new APIOpenWeather();

    protected Container container = getContentPane();

    protected JTextField cityField = new JTextField();

    private final JLabel cityLabel = new JLabel(WeatherLiterals.CITY_LABEL);

    private final JButton weatherFindButton = new JButton(WeatherLiterals.WEATHER_BUTTON);

    private final JButton exit = new JButton(WeatherLiterals.EXIT);

    private final  JTextArea weatherTextArea = new JTextArea();

    protected String cityUser;

    @Override
    public void setComponentsStyle() {
        cityField.setBounds(100, 10, 150, 30);
        cityLabel.setBounds(50, 10, 100, 30);
        weatherFindButton.setBounds(100, 40, 150, 30);
        exit.setBounds(50,300,150,30);
        container.setLayout(null);
        weatherTextArea.setBounds(300, 10, 300, 400);
        weatherTextArea.setEditable(false);
    }

    @Override
    public void addComponentsToContainer() {
        container.add(cityLabel);
        container.add(exit);
        container.add(cityField);
        container.add(weatherFindButton);
        container.add(weatherTextArea);
    }

    @Override
    public void addListeners() {
        weatherFindButton.addActionListener(e ->{
            String cityText = cityField.getText();
            System.out.println(cityText);
            if (cityText.equals("")) {
                JOptionPane.showMessageDialog(this, WeatherLiterals.EMPTY_FIELDS_DIALOG);
            }else{
                weatherTextArea.setText(apiOpenWeather.getReadyForecast(cityText));
            }
        });
        exit.addActionListener(e ->{
            setVisible(false);
        });
    }

    @Override
    public void build(String city) {
        if(city != null) {
            cityUser = city;
            weatherTextArea.setText(apiOpenWeather.getReadyForecast(cityUser));
        }
        setComponentsStyle();
        addComponentsToContainer();
        addListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        setResizable(false);
        setVisible(true);
        pack();
    }
}
