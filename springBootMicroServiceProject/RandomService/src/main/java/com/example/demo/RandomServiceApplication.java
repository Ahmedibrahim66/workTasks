package com.example.demo;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
@EnableCircuitBreaker
public class RandomServiceApplication {

	@Autowired
	private FeignRandomClient feignRandomClient;

	@Qualifier("eurekaClient")
	@Autowired
	private EurekaClient discoveryClient;

	Logger logger = LogManager.getLogger(RandomServiceApplication.class);



	public static void main(String[] args) {
		SpringApplication.run(RandomServiceApplication.class, args);
	}

	@Value("${test.string}")
	String test;

	@RequestMapping(value = "/")
	public String test(){
		return test;
	}


	@RequestMapping(value = "/person")
	public Person getPersonFromExternalAPI(){
		//getting the person data from the external api
		return feignRandomClient.getPerson();
	}

	@RequestMapping(value = "/anotherrandomserviceurl")
	@HystrixCommand(fallbackMethod = "getAnotherRandomServiceUrlFallBack",
					commandProperties = {
							@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
							@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
							@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
							@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
					}
	)
	public StringModel getAnotherRandomServiceURL(){
		//instance of discovery client to get use of the eureka
		InstanceInfo instance = discoveryClient.getNextServerFromEureka("ANOTHERRANDOMAPPLICATION", false);

		//log the url of the server we are calling
		logger.info("http://"+instance.getIPAddr()+":" + instance.getPort());

		//calling the server using the feign builder
		AnotherFeignClient anotherFeignClient = Feign.builder()
				.decoder(new GsonDecoder())
				.target(AnotherFeignClient.class, "http://"+instance.getIPAddr()+":" + instance.getPort());

		return anotherFeignClient.getString();
	}

	public StringModel getAnotherRandomServiceUrlFallBack(){
		return new StringModel("This is the fallback method");
	}

}
