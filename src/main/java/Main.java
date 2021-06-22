import lombok.extern.slf4j.Slf4j;
import view.ApplicationRunner;

/**
 * Класс - точка входа основного приложения
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Application started");
        ApplicationRunner.init();
    }
}
