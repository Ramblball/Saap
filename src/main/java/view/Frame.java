package view;

public interface Frame {

    /**
     * Метод для установки параметров размещения элементов в окне
     */
    void setComponentsStyle();

    /**
     * Метод для добавления элементов к окну
     */
    void addComponentsToContainer();

    /**
     * Метод для добавления обработчиков к элементам окна
     */
    void addListeners();

    /**
     * Метод для сборки и настройки параметров окна
     */
    void build();
}
