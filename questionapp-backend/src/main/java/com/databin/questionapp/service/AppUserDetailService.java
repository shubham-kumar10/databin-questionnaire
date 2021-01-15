package com.databin.questionapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.databin.questionapp.exception.UserAlreadyExistsException;
import com.databin.questionapp.model.AppUser;
import com.databin.questionapp.model.Role;
import com.databin.questionapp.model.User;
import com.databin.questionapp.repository.RoleRepository;
import com.databin.questionapp.repository.UserRepository;

@Component
public class AppUserDetailService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailService.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("USER not found!");
		} else {
			LOGGER.info("user is:" + user);
			AppUser appUser = new AppUser(user);
			LOGGER.info("userDetails is: " + appUser);
			return appUser;
		}

	}

	public AppUserDetailService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public void signup(User newUser) throws UserAlreadyExistsException {
		LOGGER.info("NEW USER IS: " + newUser);
		User user = userRepository.findByUsername(newUser.getUsername());
		if (user != null) {
			throw new UserAlreadyExistsException();
		} 
		else {
			Role role = roleRepository.findById(1).get();
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			newUser.setRoles(roles);
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
			newUser.setPassword(encodedPassword);
			userRepository.save(newUser);
		}

	}

}
