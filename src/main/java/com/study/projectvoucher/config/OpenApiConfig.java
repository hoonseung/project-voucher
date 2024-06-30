package com.study.projectvoucher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
public class OpenApiConfig {

    // http://localhost:9000/swagger-ui/index.html
    @Bean
    public OpenAPI openAPiInfo(){
        return new OpenAPI()
                .info(new Info()
                        .title("Practice Voucher API")
                        .description("Practice Voucher API")
                        .version(ZonedDateTime.now().toString()));
    }
}
