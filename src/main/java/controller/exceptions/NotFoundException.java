package controller.exceptions;

/**
 * Не удалось найти искомый ресурс
 */
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
