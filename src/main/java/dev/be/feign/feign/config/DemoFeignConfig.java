package dev.be.feign.feign.config;

import dev.be.feign.feign.intercepter.DemoFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Demo 관련된 config
@Configuration
public class DemoFeignConfig {

    @Bean
    public DemoFeignInterceptor feignInterceptor() {
        return DemoFeignInterceptor.of();
    }
}
