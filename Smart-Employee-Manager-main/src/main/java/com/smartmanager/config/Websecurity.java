package com.smartmanager.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class Websecurity
{
	@Autowired
	private UserDetailsService userdetailsservice;
    @Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userdetailsservice);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoAuthenticationProvider;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers("/Admin/**").hasRole("ADMIN")
								.requestMatchers("/Member/**").hasRole("MEMBER")
								.requestMatchers("/user/**").hasRole("USER")
								.anyRequest().permitAll()
				)
				.formLogin(formLogin ->
						formLogin
								.loginPage("/login")
								.successHandler((request, response, authentication) -> {
									// Determine the target URL based on user's role
									String targetUrl = determineTargetUrl(authentication);
									response.sendRedirect(targetUrl);
								})
				)
				.csrf(csrf -> csrf.disable());

		http.authenticationProvider(authenticationProvider());

		DefaultSecurityFilterChain build = http.build();
		return build;
	}
	private String determineTargetUrl(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return "/Admin/dashboard"; // Redirect to admin dashboard
		}
		else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEMBER"))) {
			return "/Member/MemberProfile"; // Redirect to admin dashboard}
		}else {
			return "/user/dashboard"; // Redirect to user dashboard
		}
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}
}