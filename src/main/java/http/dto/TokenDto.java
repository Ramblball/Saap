package http.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO класс для получения токена авторизации
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenDto {

    @SerializedName("tokenType")
    String prefix;
    @SerializedName("accessToken")
    String token;
}
