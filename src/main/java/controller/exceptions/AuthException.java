package controller.exceptions;

/**
 * Класс ошибки при прохождении аутентификации
 */
public class AuthException extends Exception {
    public AuthException(String message) {
        super(message);
    }
}
