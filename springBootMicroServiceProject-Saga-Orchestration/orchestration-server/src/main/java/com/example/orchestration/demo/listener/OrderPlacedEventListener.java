package com.example.orchestration.demo.listener;

import com.example.orchestration.demo.exception.ResourceNotFoundException;
import com.example.orchestration.demo.feignClients.OrderFeignClient;
import com.example.orchestration.demo.feignClients.StockFeignClient;
import com.example.orchestration.demo.kafka.channel.OrchestratorChannel;
import com.example.orchestration.demo.kafka.message.OrderEvent;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;



@Component
public class OrderPlacedEventListener {


	private static final Logger logger = LoggerFactory.getLogger(OrderPlacedEventListener.class);

	@Qualifier("eurekaClient")
	@Autowired
	private EurekaClient discoveryClient;


	@StreamListener(target = OrchestratorChannel.INPUT_ORDER)
	@HystrixCommand(fallbackMethod = "orderNotPlaced",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			}
	)
	public void listenOrderPlaced(@Payload OrderEvent orderEvent) {

		if (OrderEvent.OrderAction.ORDERPLACED.equals(orderEvent.getAction())) {
			logger.info("Received an OrderPlacedEvent for order id: " + orderEvent.getOrderId());
			logger.info("Going to call inventory service for order id : " + orderEvent.getOrderId());

			//instance of discovery client to get use of the eureka
			InstanceInfo instance = discoveryClient.getNextServerFromEureka("ANOTHERRANDOMAPPLICATION", false);


			//calling the server using the feign builder
			StockFeignClient stockFeignClient = Feign.builder()
					.decoder(new GsonDecoder())
					.target(StockFeignClient.class, "http://"+instance.getIPAddr()+":" + instance.getPort()+"/fetchitem");
			stockFeignClient.fetchItem();
		}
	}

	public void orderNotPlaced(@Payload OrderEvent orderEvent) {
		logger.info("order was not placed, error in calling feign server- hystrix fallback command for listenOrderPlaced");

		//getting the instance from eureka server
		InstanceInfo instance = discoveryClient.getNextServerFromEureka("RANDOMSERVICE", false);


		//calling the server using the feign builder
		OrderFeignClient orderFeignClient = Feign.builder()
				.decoder(new GsonDecoder())
				.target(OrderFeignClient.class, "http://"+instance.getIPAddr()+":" + instance.getPort()+"/compensate/order");
		orderFeignClient.compensateOrder();
	}




}
