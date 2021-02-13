package com.assessment.speer.dto;

public class AuthenticateResponseDto {

	private String token;
	private String message;

	public AuthenticateResponseDto() {
	}

	public AuthenticateResponseDto(String token, String message) {
		this.token = token;
		this.message = message;
	}

	public AuthenticateResponseDto(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
