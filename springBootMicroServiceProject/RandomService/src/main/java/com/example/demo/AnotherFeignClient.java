package com.example.demo;

import feign.RequestLine;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public interface AnotherFeignClient {
    @RequestLine("GET")
    StringModel getString();
}
