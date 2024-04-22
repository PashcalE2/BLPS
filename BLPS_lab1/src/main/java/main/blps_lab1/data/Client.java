package main.blps_lab1.data;

import jakarta.persistence.*;

@Entity
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "card_serial", nullable = true, length = -1)
    private String cardSerial;

    public String getCardSerial() {
        return cardSerial;
    }

    public void setCardSerial(String cardSerial) {
        this.cardSerial = cardSerial;
    }

    @Basic
    @Column(name = "card_validity", nullable = true, length = -1)
    private String cardValidity;

    public String getCardValidity() {
        return cardValidity;
    }

    public void setCardValidity(String cardValidity) {
        this.cardValidity = cardValidity;
    }

    @Basic
    @Column(name = "card_cvv", nullable = true, length = -1)
    private String cardCvv;

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (password != null ? !password.equals(client.password) : client.password != null) return false;
        if (cardSerial != null ? !cardSerial.equals(client.cardSerial) : client.cardSerial != null) return false;
        if (cardValidity != null ? !cardValidity.equals(client.cardValidity) : client.cardValidity != null)
            return false;
        if (cardCvv != null ? !cardCvv.equals(client.cardCvv) : client.cardCvv != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (cardSerial != null ? cardSerial.hashCode() : 0);
        result = 31 * result + (cardValidity != null ? cardValidity.hashCode() : 0);
        result = 31 * result + (cardCvv != null ? cardCvv.hashCode() : 0);
        return result;
    }
}
