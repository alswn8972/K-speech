package com.kspeech.game.db.repository;

import com.kspeech.game.db.entity.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RankDao {
    public static final  String HASH_KEY = "Rank";
    @Autowired
    private RedisTemplate template;

    public List<Rank> findRankByType(int type){
        System.out.println("Dao :"+type);
        System.out.println(template.opsForHash().get(HASH_KEY, type));
        return null;
    }
}
