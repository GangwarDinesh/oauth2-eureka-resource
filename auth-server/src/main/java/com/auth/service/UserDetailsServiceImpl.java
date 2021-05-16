package com.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.model.AuthUserDetail;
import com.auth.model.User;
import com.auth.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or Password is incorrect"));
		
		UserDetails userDetails = new AuthUserDetail(optionalUser.get());
		new AccountStatusUserDetailsChecker().check(userDetails);
		return userDetails;
	}

}
