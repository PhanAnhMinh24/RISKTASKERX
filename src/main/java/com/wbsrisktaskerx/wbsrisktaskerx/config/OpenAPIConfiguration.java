package com.wbsrisktaskerx.wbsrisktaskerx.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("WBS Risk TaskerX API")
                        .description("API documentation for WBS Risk TaskerX")
                        .version("3.0"))
                .addServersItem(new Server().url("/"))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearer-token"))
                .components(new Components().addSecuritySchemes("bearer-token",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")));
    }

}
