package main.blps_lab4.exception;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;

@Slf4j
public class BaseException extends BpmnError {
    public BaseException(String message) {
        super("none", message);
        log.error(message);
    }
}
