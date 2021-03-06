package com.example.orchestration.demo.listener;

import com.example.orchestration.demo.exception.ResourceNotFoundException;
import com.example.orchestration.demo.feignClients.OrderFeignClient;
import com.example.orchestration.demo.feignClients.StockFeignClient;
import com.example.orchestration.demo.kafka.channel.OrchestratorChannel;
import com.example.orchestration.demo.kafka.message.ItemEvent;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ItemOutOfStockListener {

//	@Autowired
//	private RestClient restClient;

	@Value("${order.service.endpoint}")
	private String orderEndpoint;

	private static final Logger logger = LoggerFactory.getLogger(ItemOutOfStockListener.class);

	@StreamListener(OrchestratorChannel.INPUT_INVNETORY)
	public void listenOutOfStockItem(@Payload ItemEvent itemEvent) throws ResourceNotFoundException {
		
		if (ItemEvent.Action.ITEMOUTOFSTOCK.equals(itemEvent.getAction())) {
			logger.info("ItemOutOfStock event received for item id: " + itemEvent.getItemId());

			logger.info("Going to call order service to compensate order for id " + itemEvent.getOrderId());

			//calling the server using the feign builder
			OrderFeignClient orderFeignClient = Feign.builder()
					.decoder(new GsonDecoder())
					.target(OrderFeignClient.class, "http://localhost:8083/compensate/order");
			orderFeignClient.compensateOrder();

			//restClient.doPost(orderEndpoint + "compensate", itemEvent.getOrderId());
		}
	}
}
