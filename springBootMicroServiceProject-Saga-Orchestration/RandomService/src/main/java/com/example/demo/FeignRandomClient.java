package com.example.demo;


import com.example.demo.models.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "https://run.mocky.io/v3/881ea0a0-3625-4b7b-bdf0-3a9ccf0baa9b" , name = "FeignClient")
public interface FeignRandomClient {

    @RequestMapping(method = RequestMethod.GET)
    Person getPerson();

}
