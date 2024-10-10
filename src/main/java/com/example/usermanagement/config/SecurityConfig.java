package com.example.usermanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.usermanagement.jwt.JwtService;
import com.example.usermanagement.jwtFilter.JwtAuthenticationFilter;
import com.example.usermanagement.jwtFilter.JwtAuthorizationFilter;
import com.example.usermanagement.model.RoleEntity;
import com.example.usermanagement.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) /*With this, we can be able to do role control directly in the controllers*/
public class SecurityConfig{
	
	@Autowired
	JwtService jwtService;
	
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	JwtAuthorizationFilter authorizationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager) throws Exception {
		JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(jwtService);
		authenticationFilter.setAuthenticationManager(authenticationManager);
		return http
				.csrf(csrf ->  csrf.disable())
				.authorizeHttpRequests(req -> {
					req.requestMatchers("api/v1/auth/**","api/v1/docs","api/v1/docs/**").permitAll();
					req.requestMatchers("api/v1/management/users/deleteUser/**").hasRole("ADMIN"); //If you have many urls to do role control, this section will be long
					req.anyRequest().authenticated();
				})
				.sessionManagement(session ->{
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
					session.maximumSessions(1)
						   .sessionRegistry(registry());
					})
				.addFilter(authenticationFilter)
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public SessionRegistry registry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder encoder) throws Exception {
		System.out.println("hello");
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsServiceImpl)
				.passwordEncoder(encoder)
				.and()
				.build();
	}
}
