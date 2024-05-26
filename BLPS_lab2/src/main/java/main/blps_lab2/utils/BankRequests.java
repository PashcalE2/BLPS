package main.blps_lab2.utils;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.Course;
import main.blps_lab2.exception.CantRequestBankException;
import main.blps_lab2.exception.NotEnoughMoneyOnCardException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class BankRequests {
    public static void removeMoney(BankCard bankCard, Course course) throws CantRequestBankException, NotEnoughMoneyOnCardException {
        String bankRequest = String.format("http://localhost:22600/server/pay?card_serial=%s&card_validity=%s&card_cvv=%s&money=%s",
                URLEncoder.encode(bankCard.getSerialNumber(), StandardCharsets.UTF_8),
                URLEncoder.encode(bankCard.getValidityDate(), StandardCharsets.UTF_8),
                URLEncoder.encode(bankCard.getCvv(), StandardCharsets.UTF_8),
                URLEncoder.encode(course.getPrice().toString(), StandardCharsets.UTF_8)
        );

        HttpResponse<String> response;
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(bankRequest))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new CantRequestBankException(e.getMessage());
        }

        if (HttpStatus.resolve(response.statusCode()) != HttpStatus.OK) {
            throw new NotEnoughMoneyOnCardException(bankCard.getId(), course.getPrice());
        }
    }
}
