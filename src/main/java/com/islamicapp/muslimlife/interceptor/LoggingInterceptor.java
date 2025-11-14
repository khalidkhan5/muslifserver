package com.islamicapp.muslimlife.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String REQUEST_ID = "requestId";
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_ID, requestId);
        request.setAttribute(START_TIME, System.currentTimeMillis());

        response.addHeader("X-Request-ID", requestId);

        log.info("Incoming Request: method={}, uri={}, requestId={}, clientIp={}",
                request.getMethod(),
                request.getRequestURI(),
                requestId,
                getClientIp(request));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Can be used for additional post-processing
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME);
        String requestId = (String) request.getAttribute(REQUEST_ID);
        long duration = System.currentTimeMillis() - startTime;

        log.info("Request Completed: method={}, uri={}, status={}, duration={}ms, requestId={}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration,
                requestId);

        if (ex != null) {
            log.error("Request Failed: requestId={}, error={}", requestId, ex.getMessage(), ex);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}