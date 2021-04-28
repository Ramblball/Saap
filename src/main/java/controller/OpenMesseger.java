package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenMesseger implements ActionListener {
    private static final Logger log = LoggerFactory.getLogger(OpenMesseger.class);
    public void actionPerformed(ActionEvent e) {

        log.info("user open messenger");

        System.out.println("Пользователь зашел в мессенджер");
    }
}
