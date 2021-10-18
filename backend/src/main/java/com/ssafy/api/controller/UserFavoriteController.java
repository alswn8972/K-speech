package com.ssafy.api.controller;

import com.ssafy.api.response.*;
import com.ssafy.api.service.UserFavoriteService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.UserFavorite;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 찜하기 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "찜하기 API", tags = {"Favorite Residence"})
@RestController
@RequestMapping("/api/v1/favorites")
public class UserFavoriteController {

    @Autowired
    UserFavoriteService userFavoriteService;

    @PostMapping()
    @ApiOperation(value = "관심 매물 찜하기", notes = "관심있는 매물을 찜한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> createFavoriteResidence(
            @RequestBody Long residenceId, @ApiIgnore Authentication authentication) {
        try {
            userFavoriteService.createFavoriteResidence(residenceId, authentication);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (NoSuchElementException | NullPointerException e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }

    @GetMapping()
    @ApiOperation(value = "찜한 관심 매물 조회", notes = "찜한 관심 매물인지 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserFavoriteRes> getFavoriteResidence( @ApiIgnore Authentication authentication ) {
        try {
            List<UserFavorite> userFavorites = userFavoriteService.getMyResidence(authentication);
            return ResponseEntity.status(200).body(UserFavoriteRes.of(userFavorites));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(UserFavoriteRes.of(500, "fail"));
        }
    }

    @GetMapping("/isfavorite")
    @ApiOperation(value = "찜했는지 확인", notes = "찜한 관심 매물을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "매물 있음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> checkIsFavorite(
            @ApiIgnore Authentication authentication, @RequestParam Long ResidenceId) {
        Boolean isFavorite = userFavoriteService.checkIsFavorite(authentication, ResidenceId);
        if(isFavorite == true)
            return ResponseEntity.status(409).body(BaseResponseBody.of(409, "찜한매물"));
        else
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "찜하지 않은 매물"));
    }

    @DeleteMapping()
    @ApiOperation(value = "관심 매물 찜 삭제", notes = "찜한 관심 매물을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "삭제 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> deleteFavoriteResidence(
            @RequestParam @ApiParam(value = "찜한 관심 매물 삭제", required = true) Long residenceId, @ApiIgnore Authentication authentication) {
        try {
            userFavoriteService.deleteFavoriteResidence(residenceId, authentication);
            return ResponseEntity.status(201).body(UserLoginPostRes.of(201, "Success"));
        }catch (NullPointerException e){
            return ResponseEntity.status(500).body(UserLoginPostRes.of(500, "Fail"));
        }
    }
}