package io.jmnarloch.spring.cloud.ribbon.gray;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;

import feign.Feign;

@Configuration
public class GrayAutoConfiguration {
    @Bean
    public RestTemplate metadataUpdateRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RegionController EurekaAdapter() {
        return new RegionController();
    }

    @Bean
    public FilterRegistrationBean globalSettingFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ContextFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ContextFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
        return registration;
    }

    @Bean
    @ConditionalOnClass(Feign.class)
    public FeignStrategyInterceptor feignStrategyInterceptor() {
        return new FeignStrategyInterceptor();
    }
}