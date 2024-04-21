package main.blps_lab1.data;

public interface ClientInterface {
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
