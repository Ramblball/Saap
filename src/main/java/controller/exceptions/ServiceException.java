package controller.exceptions;

/**
 * Ошибка при взаимодействии с сервисами
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}
