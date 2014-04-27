package com.vikas.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vikas.dao.LoginDAOImpl;

/**
 * 
 * @author Vikas Sharma
 *
 */
@Configuration
@EnableTransactionManagement
public class DbConfig {

	@Value("${database.driverClassName}")
	private String driverClassName;

	@Value("${database.url}")
	private String url;

	@Value("${database.username}")
	private String username;

	@Value("${database.password}")
	private String password;

	/**
	 * t's not necessary to explicitly have the "destroyMethod" parameter on the
	 * Bean annotation because Spring will infer it. In this case, it will
	 * notice that the DataSource bean has a close() method available and so
	 * will automatically register it as the destroyMethod.
	 * 
	 * @return
	 */
	@Bean(destroyMethod = "close")
	public BasicDataSource dataSource() {

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);

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
