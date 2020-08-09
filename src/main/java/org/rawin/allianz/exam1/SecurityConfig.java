package org.rawin.allianz.exam1;

import javax.sql.DataSource;

import org.rawin.allianz.exam1.ext.CustomRestApiAuthenticationProvider;
import org.rawin.allianz.exam1.ext.TokenAuthenticationFilter;
import org.rawin.allianz.exam1.service.RestAuthenticationSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	RestAuthenticationSupportService restAuthSupportService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/api/*")
					.hasRole("USER")

				.antMatchers("/login")
					.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.addFilterBefore(new TokenAuthenticationFilter(), BasicAuthenticationFilter.class);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new CustomRestApiAuthenticationProvider(restAuthSupportService));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
