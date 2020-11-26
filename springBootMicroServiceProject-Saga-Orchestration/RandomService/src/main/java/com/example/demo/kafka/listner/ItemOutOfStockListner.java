package com.example.demo.kafka.listner;

import com.example.demo.kafka.channel.OrderChannel;
import com.example.demo.kafka.message.ItemEvent;
import com.example.demo.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class ItemOutOfStockListner {

    @Autowired
    private OrderService orderService;

    //TODO: make the logger
    private static final Logger logger =  LoggerFactory.getLogger(ItemOutOfStockListner.class);

    @StreamListener(OrderChannel.INPUT)
    public void listenOutOfStockItem(@Payload ItemEvent itemEvent) {
        if (itemEvent.getAction().equals(ItemEvent.Action.ITEMOUTOFSTOCK)) {
            logger.info("ItemOutOfStock event received for item id: " + itemEvent.getItemId());

            logger.info("Going to compensate order for id " + itemEvent.getOrderId());
            //TODO:// make order compensate
            orderService.compensateOrder(itemEvent.getOrderId());
        }
    }
}
