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
package io.jmnarloch.spring.cloud.ribbon.support;

import java.util.concurrent.atomic.AtomicBoolean;

import io.jmnarloch.spring.cloud.ribbon.api.RibbonFilterContext;

/**
 * The Ribbon filter context holder.
 *
 * @author Jakub Narloch
 */
public class RibbonFilterContextHolder {
    private static final AtomicBoolean ADAPTER_CHANGEED = new AtomicBoolean(false);

    private static RibbonFilterContextHolderAdapter adapter = new DefaultRibbonFilterContextHolderAdapter();

    public static void setRibbonFilterContextHolderAdapter(RibbonFilterContextHolderAdapter adapter) {
        if (ADAPTER_CHANGEED.compareAndSet(false, true)) {
            RibbonFilterContextHolder.adapter = adapter;
        }
    }

    /**
     * Retrieves the current thread bound instance of {@link RibbonFilterContext}.
     *
     * @return the current context
     */
    public static RibbonFilterContext getCurrentContext() {
        ADAPTER_CHANGEED.set(true);
        return adapter.getCurrentContext();
    }

    /**
     * Clears the current context.
     */
    public static void clearCurrentContext() {
        adapter.clearCurrentContext();
    }
}
