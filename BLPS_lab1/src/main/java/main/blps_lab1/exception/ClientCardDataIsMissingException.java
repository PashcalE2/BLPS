package main.blps_lab1.exception;

public class ClientCardDataIsMissingException extends Exception {
    private final Long client_id;

    public ClientCardDataIsMissingException(Long client_id) {
        super("Нет данных для оплаты курса");
        this.client_id = client_id;
    }

    public Long getClientId() {
        return client_id;
    }

}
