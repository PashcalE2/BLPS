package main.blps_lab1.exception;

public class CantRequestBankException extends Exception {
    private final String error;

    public CantRequestBankException(String error) {
        super("Невозможно связаться с банком");
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
