package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class ClientAlreadySignedUpException extends BaseException {
    private final Long clientId;
    private final Long courseId;

    public ClientAlreadySignedUpException(Long clientId, Long courseId) {
        super("Вы уже записаны на этот курс");
        this.clientId = clientId;
        this.courseId = courseId;
    }
}
