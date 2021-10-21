package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.UserDetail;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.util.NoSuchElementException;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping()
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 409, message = "이미 존재하는 사용자 아이디")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
        String userId = registerInfo.getUserId();
        try {
            userService.getUserByUserId(userId);
            return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID입니다."));
        } catch (NoSuchElementException e) {
            userService.createUser(registerInfo);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "회원가입에 성공하셨습니다."));
        }
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "아이디 중복 조회", notes = "회원 가입시 아이디 중복 조회를 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "아이디 중복")
    })
    public ResponseEntity<BaseResponseBody> checkIdDuplicate(@PathVariable String userId){
        try {
            userService.getUserByUserId(userId);
            return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID입니다."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "이용 가능한 아이디입니다."));
        }
    }

    @GetMapping("nickName/{userNickName}")
    @ApiOperation(value = "닉네임 중복 조회", notes = "회원 가입시 닉네임 중복 조회를 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "닉네임 중복")
    })
    public ResponseEntity<BaseResponseBody> checkNickNameDuplicate(@PathVariable String userNickName){
        try {
            userService.getUserByUserNickName(userNickName);
            return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 닉네임입니다."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "이용 가능한 닉네임입니다."));
        }
    }

    @GetMapping("/me")
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
        UserDetail userDetail = (UserDetail) authentication.getDetails();
        User user = userService.getUserByUserId(userDetail.getUsername());
        return ResponseEntity.status(200).body(UserRes.of(user));
    }

    @PatchMapping(value = {"/{userId}"})
    @ApiOperation(value = "회원 본인 정보 수정", notes = "회원 본인의 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "수정 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> patchUser(
            @RequestBody @ApiParam(value = "회원 수정 정보", required = true) UserRegisterPostReq registerInfo) {
        String userId = registerInfo.getUserId();
        try {
            userService.patchUser(registerInfo, userId);
            return ResponseEntity.status(201).body(UserLoginPostRes.of(201, "회원정보 수정을 완료하였습니다."));
        }catch (Exception e){
            return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "입력내용을 확인해주세요."));
        }
    }

    @DeleteMapping(value = {"/{userId}"})
    @ApiOperation(value = "회원 삭제", notes = "로그인한 회원 아이디를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 401, message = "삭제 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> deleteUserInfo(@ApiIgnore Authentication authentication, @PathVariable String userId) {
        try {
            userService.deleteUserByUserId(userId);
            return ResponseEntity.status(200).body(UserLoginPostRes.of(201, "Success"));
        }catch (Exception e){
            return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Fail"));
        }
    }

}
