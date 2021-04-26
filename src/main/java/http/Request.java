package http;

import com.google.gson.JsonObject;

import java.util.Optional;

public interface Request {
    Optional<String> send(JsonObject object);
}
