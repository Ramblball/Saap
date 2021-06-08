package http.dto;

import http.Dto;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public enum UserDTO {;
    private interface Name { @NotBlank String getName(); }
    private interface Password { @NotBlank String getPassword(); }
    private interface Age { @Positive Integer getAge(); }
    private interface City { @NotBlank String getCity(); }

    public enum Request {;
        @Value
        public static class Login implements Name, Password, Dto {
            String name;
            String password;
        }
        @Value
        public static class Register implements Name, Password, Age, City, Dto {
            String name;
            String password;
            String city;
            Integer age;
        }
        @Value
        public static class Friend implements Name, Dto{
            String name;
        }
    }
}
