package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class CantRequestBankException extends BaseException {
    private final String error;

    public CantRequestBankException(String error) {
        super("Невозможно связаться с банком");
        this.error = error;
    }
}
