package com.ssafy.api.controller;

import com.ssafy.api.response.GameWordRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.response.WordListRes;
import com.ssafy.api.service.GameService;
import com.ssafy.api.service.RedisService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Word;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "게임 API", tags = {"Game"})
@RestController
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private GameService gameService;

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
    public ResponseEntity<?> getRedisStringValue() {
        Optional<List<Word>> wordList= null;
        try{
            wordList=gameService.getWord();
        }catch (Exception e){
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "잘못된 접근입니다."));
        }
        return ResponseEntity.status(201).body(WordListRes.of(wordList));
    }

}
