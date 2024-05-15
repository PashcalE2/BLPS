package main.blps_lab2.data;

public interface UserInterface {
    Long getId();

    String getEmail();
    void setEmail(String email);

    String getPassword();
    void setPassword(String password);

    String getCardSerial();
    void setCardSerial(String cardSerial);

    String getCardValidity();
    void setCardValidity(String cardValidity);

    String getCardCvv();
    void setCardCvv(String cardCvv);
}
