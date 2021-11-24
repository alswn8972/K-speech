package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.Sentence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceRedisRepository extends CrudRepository<Sentence, Long> {
}
