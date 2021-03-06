package com.example.demo.kafka.source;

import com.example.demo.kafka.channel.InventoryChannel;
import com.example.demo.kafka.message.ItemEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class ItemOutOfStockEventSource {

	@Autowired
	private InventoryChannel inventoryChannel;

	public void publishItemOutOfStockEvent(Long orderId,Long itemId) {

		ItemEvent message = new ItemEvent();
		message.setItemId(itemId);
		message.setOrderId(orderId);
		message.setAction(ItemEvent.Action.ITEMOUTOFSTOCK);
		message.setItemId(itemId);
		
		MessageChannel messageChannel = inventoryChannel.outboundInventory();
		messageChannel.send(MessageBuilder.withPayload(message)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build());

	}

}
