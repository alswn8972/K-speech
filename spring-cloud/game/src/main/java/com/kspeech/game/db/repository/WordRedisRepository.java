package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRedisRepository extends CrudRepository<Word, Long> {
}
