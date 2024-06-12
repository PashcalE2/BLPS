package main.blps_lab3.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.service.RequestMetricService;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Profile("publisher")
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestsCounterFilter implements Filter {
    private final RequestMetricService requestMetricService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String request = String.format("%s:%s", httpRequest.getMethod(), httpRequest.getRequestURI());
        String status = String.valueOf(httpResponse.getStatus());

        requestMetricService.addRequestInfo(request, status);
        log.info("RequestCounter: \nrequest = {} \nstatus = {}", request, status);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
