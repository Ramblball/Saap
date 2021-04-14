package http;

import java.io.IOException;

public interface Request {
    String send(String data, String path) throws IOException, InterruptedException;
}
