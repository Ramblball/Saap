package http.dto;

import http.Dto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO класс для получения данных о сервисе с одним параметром в url
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceParamDto implements Dto {

    String serviceToken;
    String value;
}
