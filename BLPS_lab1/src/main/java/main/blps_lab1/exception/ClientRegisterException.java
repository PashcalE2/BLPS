package main.blps_lab1.exception;

public class ClientRegisterException extends Exception {
    private final String email;

    public ClientRegisterException(String email) {
        super("Некорректный формат почты");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
