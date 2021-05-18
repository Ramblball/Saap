package http.payload;

import http.PayLoad;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.logging.Level;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatingUser implements PayLoad {
    String city;
}
