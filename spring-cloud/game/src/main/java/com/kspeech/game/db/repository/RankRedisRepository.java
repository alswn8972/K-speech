package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.Rank;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankRedisRepository extends CrudRepository<Rank, Long> {


}
