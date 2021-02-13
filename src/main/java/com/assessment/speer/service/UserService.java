package com.assessment.speer.service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assessment.speer.dto.AuthenticateRequestDto;
import com.assessment.speer.dto.AuthenticateResponseDto;
import com.assessment.speer.dto.UserDto;
import com.assessment.speer.entities.UserEntity;
import com.assessment.speer.repository.UserRepository;
import com.assessment.speer.util.JWTUtil;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity respEntity = userRepository.findByUserName(username);
		if (respEntity == null) {
			throw new UsernameNotFoundException("UserName not found");
		}
		return new User(respEntity.getUserName(), respEntity.getPassword(), new ArrayList<>());
	}

	public AuthenticateResponseDto generateAuthenticateToken(AuthenticateRequestDto authenticateRequestDto)
			throws BadCredentialsException, UsernameNotFoundException {
		try {
			UserDetails userDetails = loadUserByUsername(authenticateRequestDto.getUserName());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticateRequestDto.getUserName(), authenticateRequestDto.getPassword()));
			String jwt = jwtUtil.generatedToken(userDetails);
			return new AuthenticateResponseDto(jwt, "Success");
		} catch (BadCredentialsException exception) {
			throw new BadCredentialsException("Incorrect Password");
		} catch (UsernameNotFoundException exception) {
			throw new UsernameNotFoundException(exception.getMessage());
		}
	}

	public UserDto createUser(String userName, String password) {
		try {
			validateFields(userName, password);
		} catch (Exception e) {
			UserDto failRespDto = new UserDto();
			failRespDto.setMessage(e.getMessage());
			return failRespDto;
		}

		UserEntity respEntity = userRepository.findByUserName(userName);
		if (respEntity == null) {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserName(userName);
			userEntity.setPassword(password);

			UserEntity userResponseEntity = userRepository.save(userEntity);
			UserDto successRespDto = getUserDto(userResponseEntity);
			successRespDto.setMessage("success");
			return successRespDto;
		} else {
			UserDto failRespDto = new UserDto();
			failRespDto.setMessage("Username is already exist");
			return failRespDto;
		}

	}

	public void validateFields(String userName, String password) throws Exception {
		if (Objects.isNull(userName) || userName.isEmpty()) {
			throw new Exception("Username should not be empty");
		} else if (Objects.isNull(password) || password.isEmpty()) {
			throw new Exception("Password should not be empty");
		}

		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			throw new Exception(
					"Password should atleast has 8 characters. It must contains one lowercase, one uppercase, one symbol, and one number");
		}
	}

	public UserDto getUserDto(UserEntity userEntity) {
		UserDto userDto = new UserDto();
		userDto.setId(userEntity.getId());
		userDto.setUserName(userEntity.getUserName());
		userDto.setPassword(userEntity.getPassword());
		return userDto;
	}

}
