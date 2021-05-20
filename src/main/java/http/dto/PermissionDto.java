package http.dto;

import http.Dto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionDto implements Dto {

    String serviceToken;
    String permission;
}
