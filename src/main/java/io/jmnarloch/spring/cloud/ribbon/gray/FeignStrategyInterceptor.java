package io.jmnarloch.spring.cloud.ribbon.gray;

import java.util.Map;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

public class FeignStrategyInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        for (Map.Entry<String, String> entry : RibbonFilterContextHolder.getCurrentContext().getAttributes().entrySet()) {
            requestTemplate.header(entry.getKey(), entry.getValue());
        }
    }

}