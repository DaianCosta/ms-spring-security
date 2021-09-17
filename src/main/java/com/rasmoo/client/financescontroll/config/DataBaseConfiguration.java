package com.rasmoo.client.financescontroll.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfiguration {
	
	@Autowired
	private Environment env;
	
	@Bean
	@Primary
	public DataSource fcDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty("spring.datasource-fc.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource-fc.url"));
		dataSource.setUsername(env.getProperty("spring.datasource-fc.username"));
		dataSource.setPassword(env.getProperty("spring.datasource-fc.password"));
		
		return dataSource;
	}
	
	@Bean(name = "dsOAuth")
	public DataSource oAuthDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty("spring.datasource-oauth.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource-oauth.url"));
		dataSource.setUsername(env.getProperty("spring.datasource-oauth.username"));
		dataSource.setPassword(env.getProperty("spring.datasource-oauth.password"));
		
		return dataSource;
	}
	
}
