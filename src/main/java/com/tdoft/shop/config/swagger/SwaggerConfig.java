package com.tdoft.shop.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean("socialApiDocket")
    public Docket trackingApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API")
                .select()
                .paths(ant("/api/**"))
                .build();
    }

}
