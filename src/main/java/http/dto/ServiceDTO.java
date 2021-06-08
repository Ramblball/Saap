package http.dto;

import com.google.gson.annotations.SerializedName;
import http.Dto;
import lombok.Value;

import javax.validation.constraints.NotBlank;

public enum ServiceDTO {;

    private interface Token {
        @NotBlank String getToken();
    }

    private interface Param {
        @NotBlank String getParam();
    }

    private interface Field extends Param {
        @NotBlank String getField();
    }

    public enum Request {
        ;

        @Value
        public static class Param implements ServiceDTO.Param, Dto {
            String param;
        }

        @Value
        public static class Criteria implements Token, Field, Dto {
            String token;
            String field;
            String param;
        }

        @Value
        public static class ServiceParam implements Token, ServiceDTO.Param, Dto {
            String token;
            String param;
        }
    }

    public enum Response {
        ;

        public static class Token implements ServiceDTO.Token {
            @SerializedName("tokenType")
            String prefix;
            @SerializedName("accessToken")
            String token;

            @Override
            public String getToken() {
                return prefix + " " + token;
            }
        }
    }
}
