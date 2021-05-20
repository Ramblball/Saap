package http.dto;

import http.Dto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO класс для получения пользователей сервиса, удовлетворяющих параметру
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CriteriaDto implements Dto {

    String serviceToken;
    String criteria;
    String value;
}
