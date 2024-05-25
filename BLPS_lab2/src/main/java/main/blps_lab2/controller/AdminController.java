package main.blps_lab2.controller;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/admin")
@Slf4j
public class AdminController {
    @PostMapping(value = "/ban_user")
    public ResponseEntity<?> banUser(@RequestParam(defaultValue = "0") Long userId) {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping(value = "/unban_user")
    public ResponseEntity<?> unbanUser(@RequestParam(defaultValue = "0") Long userId) {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping(value = "/create_new_course")
    public ResponseEntity<?> createNewCourse(@RequestBody Course course) {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping(value = "/update_course")
    public ResponseEntity<?> updateCourseById(@RequestBody Course course) {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
