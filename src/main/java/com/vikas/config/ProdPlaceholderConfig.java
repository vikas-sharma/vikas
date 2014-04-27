package com.vikas.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.spring31.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author Vikas Sharma
 *
 */
@Configuration
@Profile(value = "prod")
@PropertySource("classpath:prod.properties")
public class ProdPlaceholderConfig {

	@Bean
	public EnvironmentStringPBEConfig environmentVariablesConfiguration() {

		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setPasswordEnvName("APP_ENCRYPTION_PASSWORD");

		return config;
	}

	@Bean
	public PooledPBEStringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setConfig(environmentVariablesConfiguration());
		encryptor.setPoolSize(5);

		return encryptor;
	}

	@Bean
	public EncryptablePropertyPlaceholderConfigurer propertyConfigurer() {

		EncryptablePropertyPlaceholderConfigurer enc = new EncryptablePropertyPlaceholderConfigurer(
				stringEncryptor());

		enc.setLocation(new ClassPathResource("prod.properties"));

		return enc;
	}

}
