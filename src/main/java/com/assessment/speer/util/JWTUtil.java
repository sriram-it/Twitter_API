package com.assessment.speer.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {
	
	private String SECURITY_KEY="secret";
	
	public String getUsername(String token) {
		return getClaim(token, Claims::getSubject);
	}
	
	public Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);		
	}
	
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}
	
	public String generatedToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECURITY_KEY).compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	
	
	
}
