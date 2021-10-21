package com.ssafy.api.controller;

import com.ssafy.api.service.KakaoService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepositorySupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	KakaoService kakaoservice;
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
        @ApiResponse(code = 401, message = "잘못된 비밀번호입니다.", response = BaseResponseBody.class),
        @ApiResponse(code = 404, message = "존재하지 않는 계정입니다.", response = BaseResponseBody.class),
        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
	public ResponseEntity<UserLoginPostRes> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String userId = loginInfo.getId();
		String password = loginInfo.getPassword();

		try {
			User user = userService.getUserByUserId(userId);
			System.out.println(user.getUserNickName());
			if(passwordEncoder.matches(password, user.getUserPass()))
				return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(user)));
			return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "잘못된 비밀번호입니다.", null));
		} catch (NoSuchElementException e){
			return ResponseEntity.status(404).body(UserLoginPostRes.of(404, "존재하지 않는 계정입니다.", null));
		}
	}

	@GetMapping("/kakaologin")
	@ApiOperation(value = "카카오 로그인", notes = "<strong>카카오 아이디와 패스워드</strong>를 통해 로그인 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "잘못된 비밀번호입니다.", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "존재하지 않는 계정입니다.", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginPostRes> k_login(@RequestBody @ApiParam(value="카카오토큰정보", required = true) @RequestParam String authorize_code) {
		String access_token=kakaoservice.getAccessToken(authorize_code);
		System.out.println(access_token);
		HashMap<String, String> userInfo = kakaoservice.getUserInfo(access_token);
		//System.out.println(userInfo.get("id"));
		String id=userInfo.get("id");

		try {
			User user= userService.getUserByUserId(id);
			System.out.println("계정있음");
			return ResponseEntity.ok(UserLoginPostRes.of(200, "로그인 성공", JwtTokenUtil.getToken(user)));
		} catch (NoSuchElementException e){
			System.out.println("계정ㄴㄴㄴㄴㄴㄴㄴ");
			return ResponseEntity.status(404).body(UserLoginPostRes.of(404, "존재하지 않는 계정입니다. 회원가입을 해주세용!", null));
		}
	}
}
