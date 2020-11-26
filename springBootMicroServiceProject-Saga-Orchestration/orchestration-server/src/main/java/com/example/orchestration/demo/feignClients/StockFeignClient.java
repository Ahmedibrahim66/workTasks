package com.example.orchestration.demo.feignClients;

import feign.RequestLine;

public interface StockFeignClient {

    @RequestLine("POST")
    String fetchItem();

    @RequestLine("POST")
    String compensateItem();

}
