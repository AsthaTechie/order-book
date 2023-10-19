package com.poc.orderbook.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        final String applicationName = "Order Book";
        final String description = "Order Book Application";
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName)
                        .description(description)
                );
    }

}
