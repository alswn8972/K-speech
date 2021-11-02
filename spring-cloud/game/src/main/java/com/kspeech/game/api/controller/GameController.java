package com.kspeech.game.api.controller;

import com.kspeech.game.api.response.SentenceListRes;
import com.kspeech.game.api.response.WordListRes;
import com.kspeech.game.api.service.RedisService;
import com.kspeech.game.common.response.BaseResponseBody;
import com.kspeech.game.db.entity.Sentence;
import com.kspeech.game.db.entity.Word;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Api(value = "게임 API", tags = {"Game"})
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private RedisService redisService;

//    @GetMapping(value = "/word/{level}")
//    public ResponseEntity<GameWordRes> getRedisStringValue(@PathVariable String level) {
//        GameWordRes gameWordRes = redisService.getRedisStringValue(level);
//        return ResponseEntity.status(200).body(gameWordRes);
//    }

    @GetMapping(value = "/word")
    @ApiOperation(value = "게임 할 단어 불러오는 API", notes = "단어를 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 409, message = "이미 존재하는 사용자 아이디")
    })
    public ResponseEntity<?> getRedisWord() {
        List<Word> wordList= null;
        List<Word> res = new ArrayList<>();
        Random r = new Random();
        try{
            wordList=redisService.getWord();
            int size = wordList.size();
            int a[] = new int[size];
            System.out.println(size);
            for(int i=0;i<15;i++){
                a[i] = r.nextInt(size);
                for(int j=0;j<i;j++){
                    if(a[i]==a[j]) i--;
                }
            }
            for(int i=0;i<15;i++){
                res.add(wordList.get(a[i]));
            }

        }catch (NullPointerException e){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "잘못된 접근입니다."));
        }
        return ResponseEntity.status(201).body(WordListRes.of(res));
    }

    @GetMapping(value = "/sentence")
    @ApiOperation(value = "게임 할 단어 불러오는 API", notes = "단어를 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 409, message = "이미 존재하는 사용자 아이디")
    })
    public ResponseEntity<?> getRedisSentence() {
        List<Sentence> wordSentence= null;
        List<Sentence> res = new ArrayList<>();
        Random r = new Random();
        try{
            wordSentence=redisService.getSentence();
            int sizeS = wordSentence.size();
            int arr[] = random(sizeS);

            for(int i=0;i<15;i++){
                res.add(wordSentence.get(arr[i]));
            }

        }catch (NullPointerException e){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "잘못된 접근입니다."));
        }
        return ResponseEntity.status(201).body(SentenceListRes.of(res));
    }

    public int[] random(int size){
        Random r = new Random();
        int a[]=new int[size];
        for(int i=0;i<15;i++){
            a[i] = r.nextInt(size);
            for(int j=0;j<i;j++){
                if(a[i]==a[j]) i--;
            }
        }
        return a;
    }

}
