package com.vikas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
@ImportResource("classpath:security-config.xml")
public class SecurityConfig {
}