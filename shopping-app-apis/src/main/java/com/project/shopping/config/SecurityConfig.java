package com.project.shopping.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.project.shopping.Security.CustomUserDetailService;
import com.project.shopping.Security.JwtAuthenticationEntryPoint;
import com.project.shopping.Security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	@Autowired
	private CustomUserDetailService userdetailservice;
	@Autowired
	private JwtAuthenticationEntryPoint jwtauthenticationpoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationfilter;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable()
	.cors()
	.configurationSource(configurationSource())
	.and()
	.authorizeHttpRequests()
	.antMatchers(PUBLIC_URLS).permitAll()
	.antMatchers(HttpMethod.GET).permitAll()
	.anyRequest()
	.authenticated()
	.and()
	.exceptionHandling().authenticationEntryPoint(this.jwtauthenticationpoint)
	.and()
	.sessionManagement()
	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.addFilterBefore(jwtAuthenticationfilter, UsernamePasswordAuthenticationFilter.class);
	
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userdetailservice).passwordEncoder(passwordEncoder());
	}

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
    
	
//	@Bean
//	public FilterRegistrationBean<CorsFilter> corsFilter()
//	{	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration cors = new CorsConfiguration();
//		cors.setAllowCredentials(true);
//		cors.addAllowedOriginPattern("*");
//		cors.addExposedHeader("Authorization");
//		cors.addExposedHeader("Content-Type");
//		cors.addExposedHeader("Accept");
//		cors.addAllowedMethod("POST");
//		cors.addAllowedMethod("GET");
//		cors.addAllowedMethod("DELETE");
//		cors.addAllowedMethod("PUT");
//		cors.addAllowedMethod("OPTIONS");
//		cors.setAllowedOrigins(Arrays.asList("*"));
//        cors.setAllowedMethods(Arrays.asList("GET","POST"));
//        cors.setAllowCredentials(true);
//		cors.addAllowedOrigin("*");
//		cors.addAllowedMethod("*");
//		cors.addExposedHeader("*");
//		
//		cors.setMaxAge(3600L);
//		source.registerCorsConfiguration("/**", cors);
//		FilterRegistrationBean bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
//		return bean;
//	
//	}
	
	private CorsConfigurationSource configurationSource() {
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      CorsConfiguration config = new CorsConfiguration();
	      config.addAllowedOrigin("*");
	      config.setAllowCredentials(true);
	      config.setAllowedOriginPatterns(Collections.singletonList("*"));
	      config.setAllowedOrigins(List.of("http://localhost:", "http://127.0.0.1:80", "http://example.com"));
	      config.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
	      config.setAllowedHeaders(List.of("*"));
	      config.addAllowedHeader("X-Requested-With");
	      config.addAllowedHeader("Content-Type");
	      config.addAllowedHeader("Access-Control-Allow-Origin");
	      config.addAllowedMethod(HttpMethod.POST);
	      source.registerCorsConfiguration("/**", config);
	      return source;
	    }
    
	
	
}
