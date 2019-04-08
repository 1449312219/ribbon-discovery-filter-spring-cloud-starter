package io.jmnarloch.spring.cloud.ribbon.gray;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

public class ContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest targetRequest = (HttpServletRequest) request;

        String region = targetRequest.getHeader(Constant.REGION);

        RibbonFilterContextHolder.getCurrentContext().add(Constant.REGION, region);

        try {
            chain.doFilter(request, response);
        } finally {
            RibbonFilterContextHolder.clearCurrentContext();
        }
    }

    @Override
    public void destroy() {
    }
}
