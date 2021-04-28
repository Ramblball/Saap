package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenDating implements ActionListener {
    private static final Logger log = LoggerFactory.getLogger(OpenDating.class);
    public void actionPerformed(ActionEvent e) {

        log.info("user open dating");

        System.out.println("Пользователь зашел в приложение знакомств");
    }
}
