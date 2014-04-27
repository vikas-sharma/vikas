package com.vikas.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
@ComponentScan("com.vikas")
@Import({ DevPlaceholderConfig.class, ProdPlaceholderConfig.class,
		WebConfig.class, DbConfig.class, MailConfig.class, SecurityConfig.class })
public class AppConfig {

	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:i18n/messages");
		messageSource.setFallbackToSystemLocale(false);

		return messageSource;
	}

}