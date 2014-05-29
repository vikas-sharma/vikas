package com.vikas.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vikas.dao.LoginDAOImpl;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
@Profile(value = "prod")
@EnableTransactionManagement
public class ProdDbConfig {

	@Value("${database.driverClassName}")
	private String driverClassName;

	@Value("${database.url}")
	private String url;

	@Value("${database.username}")
	private String username;

	@Value("${database.password}")
	private String password;

	@Value("${database.remove.abandoned}")
	private boolean removeAbandoned;

	@Value("${database.validation.query}")
	private String validationQuery;

	@Bean
	public BasicDataSource dataSource() {

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);

		ds.setRemoveAbandoned(removeAbandoned);
		ds.setValidationQuery(validationQuery);

		return ds;
	}

	@Bean
	public LoginDAOImpl loginDao() {

		LoginDAOImpl loginDAO = new LoginDAOImpl();
		loginDAO.setDataSource(dataSource());

		return loginDAO;
	}

	@Bean
	public DataSourceTransactionManager txManager() {

		DataSourceTransactionManager mgr = new DataSourceTransactionManager();
		mgr.setDataSource(dataSource());

		return mgr;
	}
}
