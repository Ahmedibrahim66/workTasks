package com.example.orchestration.demo.listener;

import com.example.orchestration.demo.exception.ResourceNotFoundException;
import com.example.orchestration.demo.feignClients.OrderFeignClient;
import com.example.orchestration.demo.kafka.channel.OrchestratorChannel;
import com.example.orchestration.demo.kafka.message.ItemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class ItemFetchedEventListener {

//	@Autowired
//	private RestClient restClient;

//	@Autowired
//	private OrderFeignClient orderFeignClient;


//	@Value("${payment.service.endpoint}")
//	private String paymentEndpoint;

	private static final Logger logger = LoggerFactory.getLogger(ItemFetchedEventListener.class);

	@StreamListener(OrchestratorChannel.INPUT_INVNETORY)
	public void listenItemFetchedEvent(@Payload ItemEvent itemFetchedMessage) throws ResourceNotFoundException {

		if (ItemEvent.Action.ITEMFETCHED.equals(itemFetchedMessage.getAction())) {
			logger.info("Received an ItemFetchedEvent for ITEM id: " + itemFetchedMessage.getItemId());

//			if (itemFetchedMessage.getOrderId() != null && itemFetchedMessage.getItemId() != null) {
//				logger.info("Going to call payment service for order id : " + itemFetchedMessage.getOrderId());
//
//				restClient.doPost(paymentEndpoint, itemFetchedMessage.getOrderId());
//			}
		}

	}

}
