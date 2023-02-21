package com.project.shopping.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.shopping.entity.User;
import com.project.shopping.exceptions.ResourceNotFoundException;
import com.project.shopping.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo userrepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepo.findByUseremailId(username).orElseThrow(() -> new ResourceNotFoundException("user",username,0));
		//log.info(user.getUseremailId());
		return user;
	}

}
