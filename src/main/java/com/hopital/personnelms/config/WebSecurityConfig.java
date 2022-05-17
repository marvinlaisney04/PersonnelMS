package com.hopital.personnelms.config;

import com.hopital.personnelms.filter.JWTAuthenticationFilter;
import com.hopital.personnelms.filter.JWTLoginFilter;
import com.hopital.personnelms.model.Personnel;
import com.hopital.personnelms.repository.PersonnelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private PersonnelRepository personnelRepo;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// No need authentication.
				.antMatchers("/api/personnel").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll() //
				.antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
				// Need authentication.
				.anyRequest().authenticated()
				//
				.and()
				//
				// Add Filter 1 - JWTLoginFilter
				//
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				//
				// Add Filter 2 - JWTAuthenticationFilter
				//
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		Iterable<Personnel> listPersonnel = personnelRepo.findAll();
		// String password = "123456";

		// String encrytedPassword = this.passwordEncoder().encode(password);
		// System.out.println("password: " + encrytedPassword);

		for (Personnel personnel : listPersonnel) {
			String password = personnel.getPassword();

			// String encrytedPassword = this.passwordEncoder().encode(password);
			System.out.println("Encoded password " + personnel.getPassword());

			InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> //
			mngConfig = auth.inMemoryAuthentication();

			// Defines 2 users, stored in memory.
			// ** Spring BOOT >= 2.x (Spring Security 5.x)
			// Spring auto add ROLE_
			UserDetails user = User.withUsername(personnel.getLogin()).password(password).roles("USER").build();

			mngConfig.withUser(user);
		}
	}
}
