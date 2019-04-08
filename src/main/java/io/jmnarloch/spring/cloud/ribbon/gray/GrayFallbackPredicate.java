package io.jmnarloch.spring.cloud.ribbon.gray;

import org.apache.commons.lang.StringUtils;

import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import io.jmnarloch.spring.cloud.ribbon.predicate.MetadataAwarePredicate;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

public class GrayFallbackPredicate extends MetadataAwarePredicate {
    @Override
    protected boolean apply(DiscoveryEnabledServer server) {
        String requestRegion = RibbonFilterContextHolder.getCurrentContext().get(Constant.REGION);

        if (isEquals(requestRegion, Constant.Region.GRAY)) {
            String serverRegion = server.getInstanceInfo().getMetadata().get(Constant.REGION);
            return isEquals(serverRegion, Constant.Region.PRDT);
        }

        return false;
    }

    private boolean isEquals(String actualRegion, Constant.Region expectedRegion) {
        return StringUtils.isNotEmpty(actualRegion) && expectedRegion.name().equals(actualRegion);
    }

}