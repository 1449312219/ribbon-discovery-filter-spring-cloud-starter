package io.jmnarloch.spring.cloud.ribbon.gray;

import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;

import io.jmnarloch.spring.cloud.ribbon.predicate.DiscoveryEnabledPredicate;
import io.jmnarloch.spring.cloud.ribbon.rule.MetadataAwareRule;

public class GrayMetadataAwareRule extends MetadataAwareRule {

    @Override
    protected CompositePredicate createCompositePredicate(DiscoveryEnabledPredicate discoveryEnabledPredicate,
            AvailabilityPredicate availabilityPredicate) {
        return CompositePredicate.withPredicates(discoveryEnabledPredicate, availabilityPredicate)
                .addFallbackPredicate(new GrayFallbackPredicate()).build();
    }

}
