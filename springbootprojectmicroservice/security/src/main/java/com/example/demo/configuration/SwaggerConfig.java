package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiDeatails());
    }


    private ApiInfo apiDeatails() {
        return new ApiInfo(
                "Exalt",
                "Sample API for springboot application with security",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Ahmed ibrahim", "myURL", "ahmed.ibrahim@exalt.email"),
                "API License",
                "myURL again",
                Collections.emptyList()
        );
    }


}