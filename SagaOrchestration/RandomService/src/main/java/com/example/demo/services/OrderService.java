package com.example.demo.services;

import com.example.demo.kafka.source.OrderNotProcessedEventSource;
import com.example.demo.kafka.source.OrderPlacedEventSource;
import com.example.demo.models.Order;
import com.example.demo.payload.PlaceOrderRequest;
import com.example.demo.payload.PlaceOrderResponse;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private static final Logger logger = LoggerFactory
            .getLogger(OrderService.class);

    @Autowired
    private OrderPlacedEventSource orderPlacedEventSource;

    @Autowired
    private OrderNotProcessedEventSource OrderNotProcessedEventSource;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;


    public PlaceOrderResponse createOrder(PlaceOrderRequest request) {
        Order order = new Order();

        /*order service should call inventory service to get item name by item id*/
        order.setItemName("item-xyz");

        /*order service should call customer service to get customer name by id*/
        order.setCustomerName("customer-abc");

        /*order service should assign some valid order id*/
        order.setId(234L);

//        InstanceInfo instance = discoveryClient.getNextServerFromEureka("ANOTHERRANDOMAPPLICATION", false);
//        logger.info("instance health check "+instance.getStatus());

        logger.info("Order Created - Order Id: "  + order.getId() );
        /*order service should save order*/
        //orderRepository.save(order)

        //OrderCreatedEventSource.publishOrderEvent(id);

        PlaceOrderResponse response = new PlaceOrderResponse();
        response.setMessage("order placed successfully");
        response.setOrderId(order.getId());

        logger.info("going to place orderPlacedEvent for order :"  + order.getId() );
        orderPlacedEventSource.publishOrderEvent(order.getId());

        return response;
    }

    public void compensateOrder(Long orderId) {
        /*delete order record for given order id from database*/
        logger.info("recived order not proccessed event");
        /*publish OoderNotProcessedEvent*/
        OrderNotProcessedEventSource.publishOrderNotProcessedEvent(orderId);
    }


}
