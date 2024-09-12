package main.blps_lab4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BankCardCredentials {
    private String serialNumber;
    private String validityDate;
    private String cvv;
}
