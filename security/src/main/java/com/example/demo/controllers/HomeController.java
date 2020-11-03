package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @RequestMapping(value = "/" ,  method = RequestMethod.GET)
    public String home(){
        return ("<h1> welcome <h2>");
    }

    @RequestMapping(value = "/user" ,  method = RequestMethod.GET)
    public String userHome(){
        return ("<h1> welcome user<h2>");
    }

    @RequestMapping(value = "/admin",  method = RequestMethod.GET)
    public String adminHome(){
        return ("<h1> welcome admin<h2>");
    }


}
