package org.ecom.authservice.config;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;


@Configuration

@Aspect
@Slf4j
public class AspectConfig {


    @Before("within(org.ecom.authservice.controller..*)")
    public void logApiCalls(JoinPoint joinPoint) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                        .getRequestAttributes()))
                        .getRequest();


        log.info(
                "➡️ API CALL | {} {} | {}.{}()",
                request.getMethod(),
                request.getRequestURI(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()
        );

    }

}
