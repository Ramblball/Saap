package http.payload;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Token {

    @SerializedName("tokenType")
    String prefix;
    @SerializedName("accessToken")
    String token;
}
