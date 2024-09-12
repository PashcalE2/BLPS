package main.blps_lab4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JwtRequest {
    private String login;
    private String password;
}
