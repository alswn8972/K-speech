package com.ssafy.api.controller;

import com.ssafy.api.service.RedisService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "게임 API", tags = {"Game"})
@RestController
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private RedisService redisservice;

    @PostMapping(value = "/getGameWord")
    public void getRedisStringValue(String level) {

        redisservice.getRedisStringValue(level);
    }

}
