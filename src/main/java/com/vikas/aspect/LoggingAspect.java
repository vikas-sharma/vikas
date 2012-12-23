package com.vikas.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Vikas Sharma
 */
@Aspect
public class LoggingAspect {

	private static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.vikas..*.*(..))")
	public void allMethods() {
	}

	@Before("allMethods()")
	public void logBefore(JoinPoint joinPoint) {
		LOGGER.debug("START process :: {}", joinPoint.getSignature().getName());
	}

	@After("allMethods()")
	public void logAfter(JoinPoint joinPoint) {
		LOGGER.debug("END process :: {}", joinPoint.getSignature().getName());
	}
}
