package service.weather;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class WeatherFrame extends JFrame implements Frame {

    private WeatherContoller weatherContoller = new WeatherContoller();

    private Container container = getContentPane();

    private JTextField cityField = new JTextField();

    private final JLabel cityLabel = new JLabel(WeatherLiterals.CITY_LABEL);

    private final JButton weatherFindButton = new JButton(WeatherLiterals.WEATHER_BUTTON);

    private final JButton exit = new JButton(WeatherLiterals.EXIT);

    private final  JTextArea weatherTextArea = new JTextArea();


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
                weatherTextArea.setText(weatherContoller.getWeather(cityText));
            }
        });
        exit.addActionListener(e ->{
            setVisible(false);
        });
    }

    @Override
    public void build() {
        String weather = weatherContoller.getUserWeather();
        if (weather != null){
            weatherTextArea.setText(weatherContoller.getUserWeather());
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
