package main.blps_lab1.data;


import jakarta.persistence.*;

@Entity
public class BankCard {
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
    @Column(name = "serial_number", nullable = false, length = -1)
    private String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "validity_date", nullable = true, length = -1)
    private String validityDate;

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    @Basic
    @Column(name = "cvv", nullable = true, length = -1)
    private String cvv;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Basic
    @Column(name = "money", nullable = true)
    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankCard bankCard = (BankCard) o;

        if (id != null ? !id.equals(bankCard.id) : bankCard.id != null) return false;
        if (serialNumber != null ? !serialNumber.equals(bankCard.serialNumber) : bankCard.serialNumber != null)
            return false;
        if (validityDate != null ? !validityDate.equals(bankCard.validityDate) : bankCard.validityDate != null)
            return false;
        if (cvv != null ? !cvv.equals(bankCard.cvv) : bankCard.cvv != null) return false;
        if (money != null ? !money.equals(bankCard.money) : bankCard.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (validityDate != null ? validityDate.hashCode() : 0);
        result = 31 * result + (cvv != null ? cvv.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
