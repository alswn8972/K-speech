package com.ssafy.api.service;

import com.ssafy.api.response.GameWordRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;


public interface RedisService {
    GameWordRes getRedisStringValue(String key);
}
