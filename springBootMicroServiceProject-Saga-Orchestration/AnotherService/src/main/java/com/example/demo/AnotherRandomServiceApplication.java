package com.example.demo;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AnotherRandomServiceApplication {

	@Qualifier("eurekaClient")
	@Autowired
	private EurekaClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(AnotherRandomServiceApplication.class, args);
	}


	@Value("${test.string}")
	String test;

	@RequestMapping(value = "/")
	public String test(){
		/*
		return the data from the propesties file either in application of config server
		and the url of the randomservice microservice
		 */
//		InstanceInfo instance = discoveryClient.getNextServerFromEureka("RANDOMSERVICE", false);
//		return new StringModel(test +  instance.getHomePageUrl());

		return "hello from another random service";
	}

}
