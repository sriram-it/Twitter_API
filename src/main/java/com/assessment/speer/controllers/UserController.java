package com.assessment.speer.controllers;

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
		UserDto responseUserDto = userService.createUser(userDto.getUserName(), userDto.getPassword());
		return new ResponseEntity<UserDto>(responseUserDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<AuthenticateResponseDto> generateAuthenticateToken(
			@RequestBody AuthenticateRequestDto authenticateRequestDto) {
		try {
			return new ResponseEntity<AuthenticateResponseDto>(
					userService.generateAuthenticateToken(authenticateRequestDto), HttpStatus.OK);
		} catch (BadCredentialsException exception) {
			return new ResponseEntity<AuthenticateResponseDto>(new AuthenticateResponseDto("Incorrect Password"),
					HttpStatus.UNAUTHORIZED);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<AuthenticateResponseDto>(new AuthenticateResponseDto(exception.getMessage()),
					HttpStatus.UNAUTHORIZED);
		}
	}
}
