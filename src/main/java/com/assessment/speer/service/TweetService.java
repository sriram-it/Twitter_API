package com.assessment.speer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.speer.dto.TweetDto;
import com.assessment.speer.entities.TweetEntity;
import com.assessment.speer.entities.UserEntity;
import com.assessment.speer.repository.TweetRepository;

import javassist.NotFoundException;

@Service
public class TweetService {

	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private UserService userService;
	
	public TweetDto createTweet(TweetDto tweetDto) {
		TweetEntity tweetEntity = new TweetEntity();
		tweetEntity.setContent(tweetDto.getContent());
		tweetEntity.setUser(userService.getCurrentUser());

		TweetEntity respTweetEntity = tweetRepository.save(tweetEntity);
		return convertToTweetDto(respTweetEntity);
	}
	
	
	public List<TweetDto> getTweets() {
		UserEntity currentUser = userService.getCurrentUser();
		List<TweetEntity> tweetsEntity = tweetRepository.findByUser(currentUser);
		List<TweetDto> tweetsDto = new ArrayList<>();
		for(TweetEntity tweetEntity: tweetsEntity) {
			tweetsDto.add(convertToTweetDto(tweetEntity));
		}
		return tweetsDto;
	}
	
	public TweetDto convertToTweetDto(TweetEntity tweetEntity) {
		TweetDto tweetDto = new TweetDto();
		tweetDto.setId(tweetEntity.getId());
		tweetDto.setContent(tweetEntity.getContent());
		return tweetDto;
	}


	public TweetDto updateTweet(TweetDto tweetDto) throws NotFoundException {
		Optional<TweetEntity> tweetEntity = tweetRepository.findById(tweetDto.getId());
		UserEntity currentUser = userService.getCurrentUser();
		if(tweetEntity.isPresent() && (currentUser.getId() == tweetEntity.get().getUser().getId())) {
			tweetEntity.get().setContent(tweetDto.getContent());
			TweetEntity respTweetEntity = tweetRepository.save(tweetEntity.get());
			return convertToTweetDto(respTweetEntity);
		} else {
			throw new NotFoundException("Tweet not found");
		}
	}


	public TweetDto deleteTweet(Long tweetId) throws NotFoundException {
		Optional<TweetEntity> tweetEntity = tweetRepository.findById(tweetId);
		UserEntity currentUser = userService.getCurrentUser();
		if(tweetEntity.isPresent()  && (currentUser.getId() == tweetEntity.get().getUser().getId())) {
			tweetRepository.delete(tweetEntity.get());
			TweetDto respTweetDto = new TweetDto();
			respTweetDto.setMessage("Tweet deleted successfully");
			return respTweetDto;
		} else {
			throw new NotFoundException("Tweet not found");
		}
	}

}