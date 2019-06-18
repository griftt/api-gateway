package com.griftt.zuul.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class RateLimiterFilter extends ZuulFilter {

    /**
     * 每秒放的令牌
     */
    private  final static RateLimiter RATE_LIMITER=RateLimiter.create(1);


    @Override
    public String filterType() {
        //确定过滤器类型 为前置过滤器
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //确定优先级，最优先
        return SERVLET_DETECTION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        if(!RATE_LIMITER.tryAcquire()){
            //拿不到令牌就报异常
            throw new RuntimeException("no token");
        }
        return null;
    }
}
