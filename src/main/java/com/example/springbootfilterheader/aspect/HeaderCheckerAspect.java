package com.example.springbootfilterheader.aspect;

import com.example.springbootfilterheader.annotation.HeaderChecker;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class HeaderCheckerAspect {

    @Before("@annotation(headerChecker)")
    public void doBefore(HeaderChecker headerChecker) {
        HttpServletRequest request = currentRequest();
        if (Objects.isNull(request)) {
            log.info("without request, skip");
            return;
        }

        String[] headerNames = headerChecker.headerNames();
        for (String headerName : headerNames) {
            String value = request.getHeader(headerName);
            if (StringUtils.hasText(value)) {
                continue;
            }
            log.error("Header {} is required", headerName);
            throw new IllegalArgumentException("Header " + headerName + " is required");
        }

        log.info("checked");
    }

    /**
     * Return request current thread bound or null if none bound.
     *
     * @return Current request or null
     */
    private HttpServletRequest currentRequest() {
        // Use getRequestAttributes because of its return null if none bound
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }
}

