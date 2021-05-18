package service;

import java.util.HashMap;
import java.util.Map;

public class Permission {

    private static final Map<String, String> permissions = new HashMap<>();

    static {
        permissions.put("LOCATION", "%s запрашивает доступ к вашему местоположению");
    }

    public static Map<String, String> getPermissions() {
        return permissions;
    }
}
