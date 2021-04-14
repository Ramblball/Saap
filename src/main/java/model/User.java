package model;

public class User {
    private final String id;
    private final String username;
    private final Double age;

    public User(String id, String username, Double age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Double getAge() {
        return age;
    }
}
