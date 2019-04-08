/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.spring.cloud.ribbon.predicate;

import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

/**
 * A default implementation of {@link DiscoveryEnabledServer} that matches the
 * instance against the attributes registered through
 *
 * @author Jakub Narloch
 * @see DiscoveryEnabledPredicate
 */
public class MetadataAwarePredicate extends DiscoveryEnabledPredicate {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean apply(DiscoveryEnabledServer server) {
        return hasAllAttributes(server, RibbonFilterContextHolder.getCurrentContext().getAttributes());
    }

    protected boolean hasAllAttributes(DiscoveryEnabledServer server, Map<String, String> attributes) {
        final Set<Map.Entry<String, String>> attributesEntry = Collections.unmodifiableSet(attributes.entrySet());
        final Map<String, String> metadata = server.getInstanceInfo().getMetadata();

        if (CollectionUtils.isEmpty(attributesEntry)) {
            return false;
        }

        return metadata.entrySet().containsAll(attributesEntry);
    }
}
