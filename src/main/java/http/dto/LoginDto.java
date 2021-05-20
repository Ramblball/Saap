package http.dto;

import http.Dto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO класс для авторизации пользователя
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginDto implements Dto {

    String name;
    String password;
}
