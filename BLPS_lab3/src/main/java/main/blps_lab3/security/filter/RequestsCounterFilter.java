package main.blps_lab3.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import main.blps_lab3.service.RequestMetricService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Profile("publisher")
@Component
@RequiredArgsConstructor
public class RequestsCounterFilter implements Filter {
    private final RequestMetricService requestMetricService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        requestMetricService.addRequestInfo(
                String.format("%s:%s", httpRequest.getMethod(), httpRequest.getRequestURI()),
                String.valueOf(httpResponse.getStatus())
        );

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
