package service.dating.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import service.dating.exception.JMXException;
import service.dating.model.Permission;
import service.dating.model.User;

import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс контроллер для приложения знакомств
 */
@Slf4j
public class DatingController {

    private static final String TOKEN = "60a5a1069d190428679fab38";
    private static final String NAME = "Dating";

    private static final Gson gson = new Gson();
    private JMXController jmxController;

    public DatingController() {
        try {
            jmxController = JMXController.getInstance();
        } catch (IOException | MalformedObjectNameException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    /**
     * Метод для получения списка пользователей сервиса с параметром
     *
     * @return Список пользоваателей
     */
    public List<User> getUsers() {
        try {
            if (hasPermission(Permission.LOCATION) || askPermission(Permission.LOCATION)) {
                return gson.fromJson(jmxController
                        .getBean()
                        .getUsers(TOKEN, "city", getLocation().orElseThrow(JMXException::new)),
                        new TypeToken<ArrayList<User>>(){}.getType());
            }
        } catch (JMXException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
        return new ArrayList<>();
    }

    /**
     * Метод для открытия чата
     *
     * @return true - Сервис имеет право;
     * false - Сервис не имеет права;
     */
    public boolean startChat(String receiverId) {
        try {
            if (hasPermission(Permission.CHAT) || askPermission(Permission.CHAT)) {
                openChat(receiverId);
                return true;
            }
        } catch (JMXException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Метод для получения геолокации пользователя
     *
     * @return Местоположение пользователя
     */
    private Optional<String> getLocation() {
        String city = jmxController.getBean().getLocation(TOKEN);
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(city);
    }

    /**
     * Метод для открытия чата
     *
     * @param receiverId Уникальный идентификатор собеседника
     */
    private void openChat(String receiverId) throws JMXException {
        if (jmxController.getBean().openChat(TOKEN, receiverId)) {
            throw new JMXException();
        }
    }

    /**
     * Метод для проверки на наличие прав
     *
     * @param permission Право
     * @return true - Сервис имеет право;
     * false - Сервис не имеет права;
     */
    private boolean hasPermission(Permission permission) {
        return jmxController.getBean()
                .hasPermission(TOKEN, permission.toString());
    }

    /**
     * Метод для добавления права
     *
     * @param permission Право
     * @return true - Разрешено;
     * false - Отказано;
     */
    private boolean askPermission(Permission permission) {
        return jmxController.getBean()
                .askPermission(TOKEN, NAME, permission.toString());
    }
}
