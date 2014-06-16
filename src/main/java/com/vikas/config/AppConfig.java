package com.vikas.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
@ComponentScan("com.vikas")
@Import({ DevPlaceholderConfig.class, ProdPlaceholderConfig.class,
		WebConfig.class, ProdDbConfig.class, DevDbConfig.class,
		MailConfig.class, SecurityConfig.class })
public class AppConfig {

}