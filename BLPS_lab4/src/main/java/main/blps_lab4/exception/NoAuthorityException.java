package main.blps_lab4.exception;

import lombok.Getter;
import main.blps_lab4.model.RoleEnum;

@Getter
public class NoAuthorityException extends BaseException {
    private final RoleEnum wanted;
    private final RoleEnum received;

    public NoAuthorityException(RoleEnum wanted, RoleEnum received) {
        super(String.format("Полученная роль не имеет права на это действие: wanted=%s, received=%s", wanted, received));
        this.wanted = wanted;
        this.received = received;
    }
}
