package com.vikas.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vikas.dao.GameDAOImpl;
import com.vikas.dao.LoginDAOImpl;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
@Profile(value = "dev")
@EnableTransactionManagement
public class DevDbConfig {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:db-schema.sql").build();
	}

	@Bean
	public LoginDAOImpl loginDao() {

		LoginDAOImpl loginDAO = new LoginDAOImpl();
		loginDAO.setDataSource(dataSource());

		return loginDAO;
	}

	@Bean
	public GameDAOImpl gameDao() {

		GameDAOImpl gameDAO = new GameDAOImpl();
		gameDAO.setDataSource(dataSource());

		return gameDAO;
	}

	@Bean
	public DataSourceTransactionManager txManager() {

		DataSourceTransactionManager mgr = new DataSourceTransactionManager();
		mgr.setDataSource(dataSource());

		return mgr;
	}
}
