package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.SentenceRank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceRankRepository extends CrudRepository<SentenceRank, Long> {


}
