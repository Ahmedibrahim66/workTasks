package com.example.orchestration.demo.feignClients;

import feign.RequestLine;


public interface OrderFeignClient {

    @RequestLine("POST")
    String compensateOrder();

}
