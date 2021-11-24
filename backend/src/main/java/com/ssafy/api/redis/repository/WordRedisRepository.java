package com.ssafy.api.redis.repository;

import com.ssafy.api.redis.entity.Word;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface WordRedisRepository extends CrudRepository<Word, Long> {

}
