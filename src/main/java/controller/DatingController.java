package controller;

import com.google.gson.Gson;
import controller.exceptions.NotFoundException;
import http.Request;
import http.payload.DatingUser;
import http.request.UserListRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.util.Optional;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatingController {
    private static final Gson gson = new Gson();

    public static User GetUserList(DatingUser datingUser) throws NotFoundException {
        Request request = new UserListRequest();
        Optional<String> response = request.send(datingUser);
        if (response.isEmpty()){
            throw new NotFoundException("Пользователя в вашем городе не найдено");
        }
        return gson.fromJson(response.get(), User.class);
    }
}
