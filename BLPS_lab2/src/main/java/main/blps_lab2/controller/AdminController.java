package main.blps_lab2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.model.Course;
import main.blps_lab2.service.interfaces.AdminServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/admin")
@PreAuthorize(value = "hasAuthority('ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminServiceInterface adminService;

    @PostMapping(value = "/ban_user")
    public ResponseEntity<?> banUser(@RequestParam(defaultValue = "0") Long userId) {
        adminService.banUser(userId);
        return new ResponseEntity<>(String.format("Пользователь %d забанен", userId), HttpStatus.OK);
    }

    @PostMapping(value = "/unban_user")
    public ResponseEntity<?> unbanUser(@RequestParam(defaultValue = "0") Long userId) {
        adminService.unbanUser(userId);
        return new ResponseEntity<>(String.format("Пользователь %d разбанен", userId), HttpStatus.OK);
    }

    @PostMapping(value = "/create_new_course")
    public ResponseEntity<?> createNewCourse(@RequestBody Course course) {
        adminService.createNewCourse(course);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping(value = "/update_course")
    public ResponseEntity<?> updateCourseById(@RequestBody Course course) {
        adminService.updateCourseById(course);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping(value = "/load_users_from_db")
    public ResponseEntity<?> loadUsersFromDB() {
        return new ResponseEntity<>(String.format("Записано %d пользователей", adminService.loadUsersFromDB()), HttpStatus.OK);
    }

    @PostMapping(value = "/block_course")
    public ResponseEntity<?> blockCourse(@RequestParam(defaultValue = "0") Long courseId) {
        adminService.blockCourse(courseId);
        return new ResponseEntity<>(String.format("Курс %d заблокирован", courseId), HttpStatus.OK);
    }

    @PostMapping(value = "/unblock_course")
    public ResponseEntity<?> unblockCourse(@RequestParam(defaultValue = "0") Long courseId) {
        adminService.unblockCourse(courseId);
        return new ResponseEntity<>(String.format("Курс %d разблокирован", courseId), HttpStatus.OK);
    }
}
