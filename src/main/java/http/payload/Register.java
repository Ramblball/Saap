package http.payload;

import http.PayLoad;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Register implements PayLoad {

    String name;
    String password;
    String city;
    Integer age;
}
