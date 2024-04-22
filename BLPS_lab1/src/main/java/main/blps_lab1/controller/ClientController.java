package main.blps_lab1.controller;

import main.blps_lab1.data.ClientInterface;
import main.blps_lab1.data.CourseInterface;
import main.blps_lab1.service.ClientServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@CrossOrigin
@ApplicationScope
public class ClientController {
    @Autowired
    private ClientServiceInterface clientService;

    @PostMapping(value = "/client/course_sign_up")
    public @ResponseBody ResponseEntity<?> courseSignUp(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Long course_id
    ) {
        /*
            1. Найти профиль
            2. Если нет - вернуть "требуется регистрация" CONFLICT, иначе #3
            3. Если нет реквизитов - вернуть "требуется реквизит" CONFLICT, иначе #4
            4. Сделать post-запрос в банк для проведения операции
            5. Получить ответ от банка
            6. Если оплата не произошла - вернуть "недостаточно средств" CONFLICT, иначе
            7. Записать на курс, вернуть OK
        */

        Optional<CourseInterface> db_course = clientService.getCourseById(course_id);
        if (db_course.isEmpty()) {
            System.out.printf("Нет такого курса (%d)\n", course_id);
            return new ResponseEntity<>("Нет такого курса", HttpStatus.CONFLICT);
        }
        CourseInterface course = db_course.get();

        Optional<ClientInterface> db_client = clientService.findClientByEmailAndPassword(email, password);
        if (db_client.isEmpty()) {
            System.out.printf("Нет такого пользователя (%s, %s)\n", email, password);
            return new ResponseEntity<>("Неправильные данные пользователя", HttpStatus.CONFLICT);
        }
        ClientInterface client = db_client.get();

        if (clientService.isClientSignedUpForCourse(client.getId(), course.getId())) {
            System.out.printf("Пользователь (%d) уже записан на курс (%d)\n", client.getId(), course.getId());
            return new ResponseEntity<>("Пользователь уже записан на курс", HttpStatus.CONFLICT);
        }

        if (client.getCardSerial() == null || client.getCardValidity() == null || client.getCardCvv() == null) {
            System.out.printf("У пользователя (%d) нет реквизитов для оплаты\n", client.getId());
            return new ResponseEntity<>("Нужны реквизиты для оплаты курса", HttpStatus.CONFLICT);
        }

        String bankRequest = String.format("http://localhost:22600/BLPS-lab1-rolling/server/pay?card_serial=%s&card_validity=%s&card_cvv=%s&money=%s",
                URLEncoder.encode(client.getCardSerial(), StandardCharsets.UTF_8),
                URLEncoder.encode(client.getCardValidity(), StandardCharsets.UTF_8),
                URLEncoder.encode(client.getCardCvv(), StandardCharsets.UTF_8),
                URLEncoder.encode(course.getPrice().toString(), StandardCharsets.UTF_8)
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(bankRequest))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (HttpStatus.resolve(response.statusCode()) != HttpStatus.OK) {
                System.out.printf("Не удалось списать средства (%s, %d)\n", client.getCardSerial(), course.getPrice());
                return new ResponseEntity<>("Не удалось списать средства", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            System.out.println("Не удалось сделать запрос на списание средств");
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Не удалось связаться с банком", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        clientService.courseSignUp(client.getId(), course.getId());
        System.out.printf("Пользователь (%d) записался на курс (%d)\n", client.getId(), course_id);
        return new ResponseEntity<>("Пользователь записан на курс", HttpStatus.OK);
    }

    @PostMapping(value = "/client/register")
    public @ResponseBody ResponseEntity<?> clientRegister(
            @RequestParam String email,
            @RequestParam String password
    ) {
        clientService.registerClient(email, password);
        System.out.printf("Зарегистрирован пользователь:\n%s\n%s\n", email, password);
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    @PostMapping(value = "/client/set_debit_card")
    public @ResponseBody ResponseEntity<?> setDebitCard(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String card_serial,
            @RequestParam String card_validity,
            @RequestParam String card_cvv
    ) {
        clientService.updateClientCard(email, password, card_serial, card_validity, card_cvv);
        System.out.printf("Данные карты клиента (%s) обновлены:\n%s\n%s\n%s\n", email, card_serial, card_validity, card_cvv);
        return new ResponseEntity<>("Данные обновлены", HttpStatus.OK);
    }

    @GetMapping(value = "/client/get_courses")
    public @ResponseBody ResponseEntity<?> getCoursesByFilter(
            @RequestParam String filter
    ) {
        return new ResponseEntity<>(clientService.getCoursesByFilter(filter), HttpStatus.OK);
    }
}
