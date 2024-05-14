package main.blps_lab2.security;

public enum Role {
    CLIENT("client"),
    ADMIN("admin");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
