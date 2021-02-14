package com.assessment.speer.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class UserEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String userName;

	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<TweetEntity> tweets = new ArrayList<>();
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public List<TweetEntity> getTweets() {
		return tweets;
	}
	
	public void setTweets(List<TweetEntity> tweets) {
		this.tweets = tweets;
	}

}
