package com.ssafy.api.controller;

import com.ssafy.api.response.GameWordRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.RedisService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "게임 API", tags = {"Game"})
@RestController
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/word/{level}")
    public ResponseEntity<GameWordRes> getRedisStringValue(@PathVariable String level) {
        GameWordRes gameWordRes = redisService.getRedisStringValue(level);
        return ResponseEntity.status(200).body(gameWordRes);
    }

}
