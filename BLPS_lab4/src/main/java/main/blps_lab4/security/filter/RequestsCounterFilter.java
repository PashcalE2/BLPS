package main.blps_lab4.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.service.RequestMetricService;
import org.springframework.stereotype.Component;

import java.io.IOException;


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
        // log.info("RequestCounter: \nrequest = {} \nstatus = {}", request, status);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
