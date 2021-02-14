package com.assessment.speer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.speer.dto.TweetDto;
import com.assessment.speer.service.TweetService;

import javassist.NotFoundException;

@RestController
public class TweetController {

	@Autowired
	private TweetService tweetService;
	
	@RequestMapping(path="/createTweet", method = RequestMethod.POST)
	public ResponseEntity<TweetDto> createTweet(@RequestBody TweetDto tweetDto){
		return new ResponseEntity<TweetDto>(tweetService.createTweet(tweetDto), HttpStatus.OK);
	}
	
	@RequestMapping(path="/getTweets", method = RequestMethod.GET)
	public ResponseEntity<List<TweetDto>> getTweets() {
		return new ResponseEntity<List<TweetDto>>(tweetService.getTweets(), HttpStatus.OK);
	}
	
	@RequestMapping(path="/updateTweet", method = RequestMethod.PUT)
	public ResponseEntity<TweetDto> updateTweet(@RequestBody TweetDto tweetDto){
		try {
			return new ResponseEntity<TweetDto>(tweetService.updateTweet(tweetDto), HttpStatus.OK);
		} catch(NotFoundException exception) {
			TweetDto failedTweetDto = new TweetDto();
			failedTweetDto.setMessage(exception.getMessage());
			return new ResponseEntity<TweetDto>(failedTweetDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path="/deleteTweet", method = RequestMethod.DELETE)
	public ResponseEntity<TweetDto> deleteTweet(@RequestParam("tweetId") Long tweetId){
		try {
			return new ResponseEntity<TweetDto>(tweetService.deleteTweet(tweetId), HttpStatus.OK);
		} catch(NotFoundException exception) {
			TweetDto failedTweetDto = new TweetDto();
			failedTweetDto.setMessage(exception.getMessage());
			return new ResponseEntity<TweetDto>(failedTweetDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
