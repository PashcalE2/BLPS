package main.blps_lab1.controller;

import main.blps_lab1.data.ClientInterface;
import main.blps_lab1.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Optional;

@Controller
@CrossOrigin
@ApplicationScope
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

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

        Optional<ClientInterface> db_client = clientService.findClientByEmailAndPassword(email, password);

        if (db_client.isEmpty()) {
            return new ResponseEntity<>("Нет такого пользователя", HttpStatus.CONFLICT);
        }

        ClientInterface client = db_client.get();

        clientService.courseSignUp(client.getId(), course_id);
        return new ResponseEntity<>(String.format("Пользователь (%d) записался на курс (%d)", client.getId(), course_id), HttpStatus.OK);
    }

    @PostMapping(value = "/client/register")
    public @ResponseBody ResponseEntity<?> clientRegister(
            @RequestParam String email,
            @RequestParam String password
    ) {
        clientService.registerClient(email, password);
        return new ResponseEntity<>(String.format("Зарегистрирован пользователь:\n%s\n%s", email, password), HttpStatus.OK);
    }

    @PostMapping(value = "/client/set_debit_card")
    public @ResponseBody ResponseEntity<?> setDebitCard(
            @RequestParam String email,
            @RequestParam String card_serial,
            @RequestParam String card_validity,
            @RequestParam String card_cvv
    ) {
        clientService.updateClientCard(email, card_serial, card_validity, card_cvv);
        return new ResponseEntity<>(String.format("Данные карты клиента (%s) обновлены:\n%s\n%s\n%s", email, card_serial, card_validity, card_cvv), HttpStatus.OK);
    }

    @GetMapping(value = "/client/get_courses")
    public @ResponseBody ResponseEntity<?> getCoursesByFilter(
            @RequestParam String filter
    ) {
        return new ResponseEntity<>(clientService.getCoursesByFilter(filter), HttpStatus.OK);
    }
}
