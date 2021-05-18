package view;

import javax.swing.*;

public abstract class Frame extends JFrame implements IFrame {

    protected Frame() {
        super();
    }

    protected Frame(String title) {
        super(title);
    }

    /**
     * Метод для установки параметров размещения элементов в окне
     */
    protected abstract void setComponentsStyle();

    /**
     * Метод для добавления элементов к окну
     */
    protected abstract void addComponentsToContainer();

    /**
     * Метод для добавления обработчиков к элементам окна
     */
    protected abstract void addListeners();
}
