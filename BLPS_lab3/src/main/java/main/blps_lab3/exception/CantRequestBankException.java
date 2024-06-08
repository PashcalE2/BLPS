package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class CantRequestBankException extends Exception {
    private final String error;

    public CantRequestBankException(String error) {
        super("Невозможно связаться с банком");
        this.error = error;
    }
}
