package main.blps_lab1.exception;

public class ClientNotFoundException extends Exception {
    private final String login;
    private final String password;

    public ClientNotFoundException(String login, String password) {
        super("Неправильные данные пользователя");
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
