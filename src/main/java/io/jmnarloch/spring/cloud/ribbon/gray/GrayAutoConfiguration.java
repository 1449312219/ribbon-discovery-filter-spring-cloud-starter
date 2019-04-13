package io.jmnarloch.spring.cloud.ribbon.gray;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.Hystrix;

import feign.Feign;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonDiscoveryRuleAutoConfiguration;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

@Configuration
@AutoConfigureBefore(RibbonDiscoveryRuleAutoConfiguration.class)
public class GrayAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GrayMetadataAwareRule grayMetadataAwareRule() {
        return new GrayMetadataAwareRule();
    }

    @Bean
    public RestTemplate metadataUpdateRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public GrayController grayController() {
        return new GrayController();
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

    @Configuration
    @ConditionalOnClass(Feign.class)
    public class FeginConfiguration {
        @Bean
        public FeignStrategyInterceptor feignStrategyInterceptor() {
            return new FeignStrategyInterceptor();
        }
    }

    @Configuration
    @ConditionalOnClass(Hystrix.class)
    public static class HystrixConfiguration {
        @PostConstruct
        void setContextHolderAdapter() {
            RibbonFilterContextHolder
                    .setRibbonFilterContextHolderAdapter(new HystrixRibbonFilterContextHolderAdapter());
        }
    }
}