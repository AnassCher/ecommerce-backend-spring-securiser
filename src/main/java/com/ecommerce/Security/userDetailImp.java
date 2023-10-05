package com.ecommerce.Security;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.Entities.Client;
import com.ecommerce.Repository.ClientRepo;

@Service
public class userDetailImp implements UserDetailsService{

	@Autowired
	ClientRepo clientRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client c = clientRepo.findByUsername(username);
		if(c != null) {
			String[] roles = c.getRoles().stream().map(cz -> cz.getName()).collect(Collectors.toList()).toArray(String[]::new);
			return User.withUsername(c.getUsername())
					.password(c.getPassword())
					.authorities(roles)
					.build();
		}
		
		throw new UsernameNotFoundException(username+" Not found");
	}

}
