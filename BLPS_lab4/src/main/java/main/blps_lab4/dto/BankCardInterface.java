package main.blps_lab4.dto;

public interface BankCardInterface {
    Long getId();
    void setId(Long id);

    Long getUserId();
    void setUserId(Long id);

    String getSerialNumber();
    void setSerialNumber(String serialNumber);

    String getValidityDate();
    void setValidityDate(String validityDate);

    String getCvv();
    void setCvv(String cvv);

    Integer getMoney();
    void setMoney(Integer money);
}
