package http.payload;

import http.PayLoad;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CriteriaReq implements PayLoad {

    String serviceToken;
    String criteria;
    String value;
}