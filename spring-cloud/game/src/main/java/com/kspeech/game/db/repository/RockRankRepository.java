package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.RainRank;
import com.kspeech.game.db.entity.RockRank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RockRankRepository extends CrudRepository<RockRank, Long> {


}
