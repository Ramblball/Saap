package service.dating;

import service.dating.view.DatingFrame;
import service.dating.view.Frame;

import javax.swing.*;

public class DatingMain {
    public static void main(String[] args) {
        Frame frame = new DatingFrame();
        SwingUtilities.invokeLater(frame::build);
    }
}
