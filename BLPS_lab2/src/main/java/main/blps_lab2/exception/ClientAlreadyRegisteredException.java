package main.blps_lab2.exception;

public class ClientAlreadyRegisteredException extends Exception {
    private final String email;

    public ClientAlreadyRegisteredException(String email) {
        super("Такой пользователь уже зарегистрирован");
        this.email = email;
    }
}

