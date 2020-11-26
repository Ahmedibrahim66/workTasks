package com.example.demo.kafka.config;


import com.example.demo.kafka.channel.InventoryChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(InventoryChannel.class)
public class StreamsConfig {

}
