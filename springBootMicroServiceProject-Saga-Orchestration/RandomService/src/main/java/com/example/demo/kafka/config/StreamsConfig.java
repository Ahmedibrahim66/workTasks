package com.example.demo.kafka.config;

import com.example.demo.kafka.channel.OrderChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(OrderChannel.class)
public class StreamsConfig {

}
