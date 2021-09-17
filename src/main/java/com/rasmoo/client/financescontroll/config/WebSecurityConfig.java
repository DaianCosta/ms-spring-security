package com.rasmoo.client.financescontroll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rasmoo.client.financescontroll.v1.service.UserInfoService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder encorder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configuracaoGlobal(AuthenticationManagerBuilder auth, UserInfoService userInfo) throws Exception{
		auth.userDetailsService(userInfo).passwordEncoder(this.encorder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		String[] allowed = new String[] {
				"/webjars", "/v1/usuario","/static/**","/swagger/**"
		};
		
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(allowed).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
}
