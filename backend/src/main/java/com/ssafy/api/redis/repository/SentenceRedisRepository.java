package com.ssafy.api.redis.repository;

import com.ssafy.api.redis.entity.Sentence;
import org.springframework.data.repository.CrudRepository;

public interface SentenceRedisRepository extends CrudRepository<Sentence, Long> {
}
