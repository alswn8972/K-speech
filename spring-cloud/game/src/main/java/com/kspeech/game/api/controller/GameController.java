package com.kspeech.game.api.controller;

import com.kspeech.game.api.response.SentenceListRes;
import com.kspeech.game.api.response.WordListRes;
import com.kspeech.game.api.service.RedisService;
import com.kspeech.game.common.response.BaseResponseBody;
import com.kspeech.game.db.entity.*;
import com.kspeech.game.db.repository.RankDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(value = "게임 API", tags = {"Game"})
@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RankDao rankDao;

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
        try{
            wordList=redisService.getWord();
            int size = wordList.size();
            int arr[] = random(size);

            for(int i=0;i<15;i++){
                res.add(wordList.get(arr[i]));
            }

        }catch (NullPointerException e){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "게임 데이터가 존재하지 않습니다."));
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
        List<Sentence> sentenceList= null;
        List<Sentence> res = new ArrayList<>();
        try{
            sentenceList=redisService.getSentence();
            int sizeS = sentenceList.size();
            int arr[] = random(sizeS);

            for(int i=0;i<15;i++){
                res.add(sentenceList.get(arr[i]));
            }

        }catch (NullPointerException e){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "게임 데이터가 존재하지 않습니다."));
        }
        return ResponseEntity.status(201).body(SentenceListRes.of(res));
    }

    @GetMapping(value = "/total/ranking")
    @ApiOperation(value = "게임 전체 랭킹을 확인 하는 API", notes = "등수를 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "등수 없음"),
            @ApiResponse(code = 409, message = "이미 존재하는 사용자 아이디")
    })
    public ResponseEntity<?> getTotalRank() {
        Map<String, Object> res = null;

        try{
            res=redisService.getRank();
            System.out.println(res);

        }catch (NullPointerException e){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "랭킹 정보가 없습니다."));
        }
        return ResponseEntity.status(201).body(res);
    }

    @PostMapping(value = "/save/score")
    @ApiOperation(value = "게임 랭킹을 저장하는 API", notes = "등수를 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "등수 없음"),
            @ApiResponse(code = 409, message = "이미 존재하는 사용자 아이디")
    })
    public ResponseEntity<?> saveScore(@RequestBody Rank rank) {
        try{
            if(rank.getType()==1){
                WordRank w = new WordRank();
                w.setScore(rank.getScore());
                w.setUserNick(rank.getUserNick());
                redisService.addWordRank(w);

            }else if(rank.getType()==2){
                SentenceRank r = new SentenceRank();
                r.setUserNick(rank.getUserNick());
                r.setScore(rank.getScore());
                redisService.addSentenceRank(r);
            }else if(rank.getType()==3){
                RainRank r = new RainRank();
                r.setUserNick(rank.getUserNick());
                r.setScore(rank.getScore());
                redisService.addRainRank(r);
            }else{
                RockRank r = new RockRank();
                r.setUserNick(rank.getUserNick());
                r.setScore(rank.getScore());
                redisService.addRockRank(r);
            }
        }catch (NullPointerException e){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "잘못된 접근입니다."));
        }
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "서버에 점수가 저장되었습니다."));
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

    @GetMapping("message")
    public String message(){
        return "test";
    }

}
