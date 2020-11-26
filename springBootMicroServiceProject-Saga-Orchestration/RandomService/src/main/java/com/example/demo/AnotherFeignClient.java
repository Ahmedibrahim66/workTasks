package com.example.demo;

import com.example.demo.models.StringModel;
import feign.RequestLine;


public interface AnotherFeignClient {
    @RequestLine("GET")
    StringModel getString();
}
