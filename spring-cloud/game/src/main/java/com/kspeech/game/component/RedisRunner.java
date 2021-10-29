package com.kspeech.game.component;

import com.kspeech.game.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class RedisRunner implements ApplicationRunner {
    @Autowired
    RedisService redisService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisService.addWord("사랑", "사랑", 1);
        redisService.addWord("낚시꾼", "낙시꿍", 1);
        redisService.addWord("시발", "씨발", 1);
        redisService.addWord("뿌앵", "뿌애", 3);
        redisService.addWord("아유", "아유", 1);
        redisService.addWord("왜안돼", "왜안돼", 1);
        redisService.addWord("제발", "제발", 1);
        redisService.addWord("아아", "아아", 3);
        redisService.addWord("사랑", "사랑", 1);
        redisService.addWord("낚시꾼", "낙시꿍", 1);
        redisService.addWord("시발", "씨발", 1);
        redisService.addWord("뿌앵", "뿌애", 3);
        redisService.addWord("사랑", "사랑", 1);
        redisService.addWord("낚시꾼", "낙시꿍", 1);
        redisService.addWord("시발", "씨발", 1);
        redisService.addWord("뿌앵", "뿌애", 3);
    }
}
