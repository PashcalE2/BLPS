package main.blps_lab3.service.interfaces;

import main.blps_lab3.model.UserEntity;

public interface MailServiceInterface {
    void sendEmail(String userEmail, String title, String message);
}
