package com.example.demo.controllers;

import com.example.demo.AnotherFeignClient;
import com.example.demo.FeignRandomClient;
import com.example.demo.models.Order;
import com.example.demo.models.Person;
import com.example.demo.models.StringModel;
import com.example.demo.payload.PlaceOrderRequest;
import com.example.demo.payload.PlaceOrderResponse;
import com.example.demo.services.OrderService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableCircuitBreaker
public class RandomServiceController {

    @Autowired
    private FeignRandomClient feignRandomClient;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;

    Logger logger = LogManager.getLogger(RandomServiceController.class);

    @Value("${test.string}")
    String test;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/")
    public String test(){
        return test;
    }


    @RequestMapping(value = "/person")
    public Person getPersonFromExternalAPI(){
        //getting the person data from the external api
        return feignRandomClient.getPerson();
    }

    @RequestMapping(value = "/anotherrandomserviceurl")
    @HystrixCommand(fallbackMethod = "getAnotherRandomServiceUrlFallBack",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            }
    )
    public StringModel getAnotherRandomServiceURL(){
        //instance of discovery client to get use of the eureka
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("ANOTHERRANDOMAPPLICATION", false);

        //log the url of the server we are calling
        logger.info("http://"+instance.getIPAddr()+":" + instance.getPort());

        //calling the server using the feign builder
        AnotherFeignClient anotherFeignClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(AnotherFeignClient.class, "http://"+instance.getIPAddr()+":" + instance.getPort());

        return anotherFeignClient.getString();
    }

    public StringModel getAnotherRandomServiceUrlFallBack(){
        return new StringModel("This is the fallback method");
    }


    @PostMapping(value = "/order")
    public PlaceOrderResponse makeOrder(@RequestBody PlaceOrderRequest request){
        //TODO: create order service
        return orderService.createOrder(request);
    }

    @PostMapping(value = "/compensate/order")
    public void compensateOrder(@RequestBody PlaceOrderRequest request){
        //TODO: create order service
        orderService.compensateOrder(request.getItemId());
    }



}
