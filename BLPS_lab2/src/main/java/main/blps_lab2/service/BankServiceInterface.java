package main.blps_lab2.service;

public interface BankServiceInterface {
    void removeMoney(String card_serial, String validity_date, String cvv, Integer money);
}
