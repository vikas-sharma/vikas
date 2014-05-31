package com.vikas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder registry)
			throws Exception {
		registry.userDetailsService(userDetailsService).passwordEncoder(
				new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/resources");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable()
        .authorizeRequests()
            .antMatchers("/admin.htm")
            .hasAuthority("ROLE_ADMIN")
            .antMatchers("/personal/myPhotos.htm")
            .hasAnyAuthority("ROLE_USER", "ROLE_FAMILY", "ROLE_ADMIN")
            .antMatchers("/personal/familyPhotos.htm")
            .hasAnyAuthority("ROLE_FAMILY", "ROLE_ADMIN")
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin()
            .usernameParameter("j_username") // default is username
            .passwordParameter("j_password") // default is password
            .loginPage("/login.htm")
            .loginProcessingUrl("/j_spring_security_check")
            .failureUrl("/login.htm?login_error=t")
            .permitAll()
        .and()
            .logout().logoutSuccessUrl("/")
            .logoutUrl("/j_spring_security_logout")
        .and()
            .rememberMe().key("myAppKey").tokenValiditySeconds(864000);
	}
}