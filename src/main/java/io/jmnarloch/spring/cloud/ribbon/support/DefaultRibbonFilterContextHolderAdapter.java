package io.jmnarloch.spring.cloud.ribbon.support;

import io.jmnarloch.spring.cloud.ribbon.api.RibbonFilterContext;

public class DefaultRibbonFilterContextHolderAdapter implements RibbonFilterContextHolderAdapter {

    private static final ThreadLocal<RibbonFilterContext> contextHolder = new InheritableThreadLocal<RibbonFilterContext>() {
        @Override
        protected RibbonFilterContext initialValue() {
            return new DefaultRibbonFilterContext();
        }
    };

    @Override
    public RibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }

    @Override
    public void clearCurrentContext() {
        contextHolder.remove();
    }
}
