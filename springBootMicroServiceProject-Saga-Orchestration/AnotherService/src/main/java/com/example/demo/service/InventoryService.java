package com.example.demo.service;

import com.example.demo.kafka.source.ItemFetchedEventSource;
import com.example.demo.kafka.source.ItemOutOfStockEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class InventoryService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(InventoryService.class);
	
	@Autowired
	private ItemFetchedEventSource itemFetchedEventSource;
	
	@Autowired
	private ItemOutOfStockEventSource itemOutOfStockEventSource;
	
	public void fetchItem() {

		Long orderId = Long.valueOf(3);
		/*inventory service will call order service to find out item id for order id*/
		Long itemId = getItemFromOrderService(orderId);
		
		Boolean isInStock = isItemExistsInItemDatabase(itemId);
		
		if(isInStock) {
			itemFetchedEventSource.publishItemFetchedEvent(orderId,itemId);
			logger.info("item is fetched successfully");
		}else {
			itemOutOfStockEventSource.publishItemOutOfStockEvent(orderId, itemId);
			logger.info("item is out of stock");
		}
	}
	
	public void compensateItem(Long orderId) {
		/*return given item id to inventory*/
		Long itemId = getItemFromOrderService(orderId);
		logger.warn("Running compensate item for order Id:" + orderId);

		/*this will compensate order*/
		itemOutOfStockEventSource.publishItemOutOfStockEvent(orderId, itemId);
	}
	
	private Long getItemFromOrderService(Long orderId) {
		//make rest call to order service to find corresponding item
		Long itemId = 456L;
		return itemId;
	}
	
	private boolean isItemExistsInItemDatabase(Long itemId) {
		return false;
	}

}
