package service;

/**
 * Перечисление прав, доступных сервисам
 */
public enum Permission {

    LOCATION("%s запрашивает доступ к вашему местоположению"),
    CHAT("%s запрашивает доступ к возможности создать переписку");

    String message;

    Permission(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
