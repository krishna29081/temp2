package com.project.shopping.Security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		String username= null;
		String token1 = null;
		if(requestToken != null && requestToken.startsWith("Bearer"))
		{
			token1 = requestToken.substring(7);
			log.info("this is token after substring :- {}",token1);
			try {
			username = jwtTokenHelper.getUsernameFromToken(token1);
			log.info("Username from token :- {}",username);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("unable to get jwt token");
			}catch(ExpiredJwtException e)
			{
				System.out.println("jet token expired");
			}catch(MalformedJwtException e)
			{
				System.out.println("invalid jwt");
			}
			
		}else {
			System.out.println("jwt does not begin with bearer");
		}
		
		//once we get token, now validate 
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null)
		{	UserDetails userdetails = userDetailsService.loadUserByUsername(username);
			log.info("Username in validate function :- {}", userdetails.getUsername());
			if(jwtTokenHelper.validateToken(token1, userdetails))
			{	UsernamePasswordAuthenticationToken usernameauthentication = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				usernameauthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernameauthentication);
			}else {
				System.out.println("Invalid token");
			}
		}else {
			System.out.println("username is null or context is null");
		}
		filterChain.doFilter(request,response);
	}

}
