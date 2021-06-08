package http.dto;

import http.Dto;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public enum UserDTO {;

    private interface MainInfo {
        @NotBlank String getName();
        @NotBlank String getPassword();
    }

    private interface SubInfo {
        @Positive Integer getAge();
        @NotBlank String getCity();
    }

    public enum Request {;

        @Value
        public static class Login implements MainInfo, Dto {
            String name;
            String password;
        }

        @Value
        public static class Register implements MainInfo, SubInfo, Dto {
            String name;
            String password;
            String city;
            Integer age;
        }
    }
}
