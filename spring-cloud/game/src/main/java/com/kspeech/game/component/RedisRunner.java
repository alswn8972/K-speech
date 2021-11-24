package com.kspeech.game.component;

import com.kspeech.game.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
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

        redisService.addSentence("면접보세요", "면접보세요",1);
        redisService.addSentence("안녕하세요", "안녕하세요",1);
        redisService.addSentence("바보하세요", "바보하세요",1);
        redisService.addSentence("감사하세요", "감사하세요",1);
        redisService.addSentence("뻐큐하세요", "뻐큐하세요",1);
        redisService.addSentence("ㅎㅎ하세요", "ㅎㅎ하세요",1);
        redisService.addSentence("크크하세요", "크크하세요",1);
        redisService.addSentence("쿠쿠하세요", "쿠쿠하세요",1);
        redisService.addSentence("구구하세요", "구구하세요",1);
        redisService.addSentence("뿌엥하세요", "뿌엥하세요",1);
        redisService.addSentence("요염하세요", "요염하세요",1);
        redisService.addSentence("귀여우세요", "귀여우세요",1);
        redisService.addSentence("지각하세요", "지각하세요",1);
        redisService.addSentence("호이호이둘리입니다.", "호이호이둘리입니다",1);
        redisService.addSentence("바보입니다", "바보입니다",1);

    }
}
