package com.example.orchestration.demo.kafka.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrchestratorChannel {

	String INPUT_ORDER = "order-out";
	String INPUT_INVNETORY = "inventory-out";

	@Input(INPUT_ORDER)
	SubscribableChannel inboundOrder();
	
	@Input(INPUT_INVNETORY)
	SubscribableChannel inboundInventory();
	


}
