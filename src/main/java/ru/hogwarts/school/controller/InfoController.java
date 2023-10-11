package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@Profile({"dev", "prod"})
public class InfoController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/getPort")
    public String getServerPort() {
        return "The application is running on port: " + serverPort;
    }
}
