package com.ssafy.api.service;

import com.ssafy.api.response.GameWordRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public GameWordRes getRedisStringValue(String key) {
        ValueOperations<String, List<String>> stringValueOperations = redisTemplate.opsForValue();
        GameWordRes gameWord = new GameWordRes();
        gameWord.setKey(key);
        gameWord.setValue(stringValueOperations.get(key));
        return gameWord;
    }
}
