package com.assessment.speer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.speer.entities.TweetEntity;
import com.assessment.speer.entities.UserEntity;

@Repository
public interface TweetRepository extends CrudRepository<TweetEntity, Long> {
	
	List<TweetEntity> findByUser(UserEntity userEntity);

}
