package com.example.demo.controllers;

import com.example.demo.kafka.message.OrderEvent;
import com.example.demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    InventoryService inventoryService;

    @PostMapping(value = "/fetchitem")
    public void makeOrder(@RequestBody OrderEvent order){
        //TODO: create order service
        inventoryService.fetchItem(order.getOrderId());
    }

}
