package model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Класс модели данных пользователя
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class User {

    String id;
    String name;
    Integer age;
    String city;
}
