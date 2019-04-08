package io.jmnarloch.spring.cloud.ribbon.gray;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

import io.jmnarloch.spring.cloud.ribbon.api.RibbonFilterContext;
import io.jmnarloch.spring.cloud.ribbon.support.DefaultRibbonFilterContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolderAdapter;

public class HystrixRibbonFilterContextHolderAdapter implements RibbonFilterContextHolderAdapter {

    private static final HystrixRequestVariableDefault<RibbonFilterContext> CURRENT_CONTEXT = new HystrixRequestVariableDefault<RibbonFilterContext>();

    @Override
    public RibbonFilterContext getCurrentContext() {
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
            CURRENT_CONTEXT.set(new DefaultRibbonFilterContext());
        }

        return CURRENT_CONTEXT.get();
    }

    @Override
    public void clearCurrentContext() {
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }
    }
}
