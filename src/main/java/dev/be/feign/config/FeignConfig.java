package dev.be.feign.config;

import dev.be.feign.feign.logger.FeignCustomLogger;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 모든 client 일괄적
@Configuration
public class FeignConfig {

    @Bean
    public Logger feignLogger() {
        return new FeignCustomLogger();
    }

}
