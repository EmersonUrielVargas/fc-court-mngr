package com.foodcourt.court.infrastructure.out.rest.interceptor;

import com.foodcourt.court.infrastructure.shared.GeneralConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class FeingClienRequestInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest currentRequest = getCurrentHttpRequest();
        if (currentRequest != null) {
            String authHeader = currentRequest.getHeader(GeneralConstants.AUTH_HEADER_NAME);
            if (authHeader != null && authHeader.startsWith(GeneralConstants.AUTH_TOKEN_PREFIX)) {
                requestTemplate.header(GeneralConstants.AUTH_HEADER_NAME, authHeader);
            }
        }

    }

    private HttpServletRequest getCurrentHttpRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (IllegalStateException | ClassCastException e) {
            return null;
        }
    }
}
