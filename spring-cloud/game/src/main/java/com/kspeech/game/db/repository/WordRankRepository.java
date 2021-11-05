package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.Rank;
import com.kspeech.game.db.entity.WordRank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRankRepository extends CrudRepository<WordRank, Long> {


}
