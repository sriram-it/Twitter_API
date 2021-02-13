package com.assessment.speer.dto;

public class AuthenticateRequestDto {

	private String userName;
	private String password;
	
	public AuthenticateRequestDto() {
	}
	
	public AuthenticateRequestDto(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
