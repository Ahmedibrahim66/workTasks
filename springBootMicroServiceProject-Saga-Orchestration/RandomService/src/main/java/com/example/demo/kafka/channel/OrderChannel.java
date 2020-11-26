package com.example.demo.kafka.channel;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderChannel {

    String OUTPUT = "order-out";


    @Output(OUTPUT)
    MessageChannel outboundOrder();


}
