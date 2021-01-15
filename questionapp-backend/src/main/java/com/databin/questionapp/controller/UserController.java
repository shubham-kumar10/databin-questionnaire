package com.databin.questionapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.databin.questionapp.exception.UserAlreadyExistsException;
import com.databin.questionapp.model.User;
import com.databin.questionapp.service.AppUserDetailService;;

@RestController
@RequestMapping("/register")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	AppUserDetailService appUserDetailsService;

	@PostMapping
	public void signupUser(@RequestBody @Valid User newUser) throws UserAlreadyExistsException {
		LOGGER.info("Start of signupUser");
		LOGGER.debug("new User is {}",newUser);
		appUserDetailsService.signup(newUser);
		LOGGER.info("End of signupUser");
	}
	
	@GetMapping
	public String Test(){
		return "Working";
	}
	
	@PostMapping("/admin")
	public void signupAdmin(@RequestBody @Valid User newUser) throws UserAlreadyExistsException {
		appUserDetailsService.signup(newUser);
	}

}
