package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.Rank;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RankDao {
    public static final  String HASH_KEY = "Rank";
    private RedisTemplate template;

//    public List<Rank> findRankByType(int type){
//
//    }

}
