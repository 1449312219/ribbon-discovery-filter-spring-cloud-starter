package io.jmnarloch.spring.cloud.ribbon.support;

import io.jmnarloch.spring.cloud.ribbon.api.RibbonFilterContext;

public interface RibbonFilterContextHolderAdapter {

    RibbonFilterContext getCurrentContext();

    void clearCurrentContext();
}
