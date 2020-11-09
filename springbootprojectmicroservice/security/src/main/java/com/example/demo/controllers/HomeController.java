package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Value("${test.string}")
    String test;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return test;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userHome() {
        return ("<h1> welcome user<h2>");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminHome() {
        return ("<h1> welcome admin<h2>");
    }


}
