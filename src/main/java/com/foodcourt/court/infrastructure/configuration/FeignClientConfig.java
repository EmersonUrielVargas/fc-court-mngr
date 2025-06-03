package com.foodcourt.court.infrastructure.configuration;

import com.foodcourt.court.infrastructure.out.rest.interceptor.ControllerFeignErrorDecoder;
import com.foodcourt.court.infrastructure.out.rest.interceptor.FeingClienRequestInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor feignClientRequestInterceptor() {
        return new FeingClienRequestInterceptor();
    }

    @Bean
    public ErrorDecoder customFeignErrorDecoder() {
        return new ControllerFeignErrorDecoder();
    }
}
