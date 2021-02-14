package com.assessment.speer.controllers;

import javax.persistence.EntityExistsException;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.speer.dto.AuthenticateRequestDto;
import com.assessment.speer.dto.AuthenticateResponseDto;
import com.assessment.speer.dto.UserDto;
import com.assessment.speer.service.UserService;
import com.assessment.speer.util.JWTUtil;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		try {
			UserDto responseUserDto = userService.createUser(userDto.getUserName(), userDto.getPassword());
			return new ResponseEntity<UserDto>(responseUserDto, HttpStatus.OK);
		}catch(ValidationException | EntityExistsException exception){
			return new ResponseEntity<UserDto>(new UserDto(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<AuthenticateResponseDto> generateAuthenticateToken(
			@RequestBody AuthenticateRequestDto authenticateRequestDto) {
		try {
			return new ResponseEntity<AuthenticateResponseDto>(
					userService.generateAuthenticateToken(authenticateRequestDto), HttpStatus.OK);
		} catch (UsernameNotFoundException| BadCredentialsException exception) {
			return new ResponseEntity<AuthenticateResponseDto>(new AuthenticateResponseDto(exception.getMessage()),
					HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(path = "/search", method = RequestMethod.GET)
	public ResponseEntity<UserDto> findUser(@RequestParam("userName") String userName) {
		try {
			return new ResponseEntity<UserDto>(userService.findUser(userName), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<UserDto>(new UserDto(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
