package com.friday.colini.attach.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    //
    //
    //

    private ExceptionFilter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //
    //
    //

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws IOException {
        try {
            filterChain.doFilter(
                    request,
                    response
            );
        } catch (final PlatformException e) {
            recovery(
                    response,
                    e
            );
        } catch (final Throwable e) {
            log.error("Uncaught exception", e);

            recovery(
                    response,
                    PlatformException.builder().status(PlatformStatus.INTERNAL_SERVER_ERROR).build()
            );
        }
    }

    //
    //
    //

    private void recovery(
            final HttpServletResponse response,
            final PlatformException platformException
    ) throws IOException {
        final var statusCode = platformException.toResponse().getStatusCode().value();
        final var error = platformException.toResponse().getBody();
        final var errorString = objectMapper.writeValueAsString(error);

        response.setStatus(statusCode);
        response.getWriter().write(errorString);
    }
}
