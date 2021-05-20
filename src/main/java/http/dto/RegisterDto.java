package http.dto;

import http.Dto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO класс для регистрации пользователя
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterDto implements Dto {

    String name;
    String password;
    String city;
    Integer age;
}
