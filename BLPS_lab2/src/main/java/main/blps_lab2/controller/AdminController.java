package main.blps_lab2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/admin")
@Slf4j
public class AdminController {
    @PostMapping(value = "/ban_user")
    public void banUser() {

    }

    @PostMapping(value = "/unban_user")
    public void unbanUser() {

    }

    @PostMapping(value = "/create_new_course")
    public void createNewCourse() {

    }

    @PostMapping(value = "/update_course")
    public void updateCourseById(Long course_id, String name, Integer price) {

    }
}
