package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.db.entity.EstateInfo;
import com.ssafy.db.entity.User;
import org.springframework.http.ResponseEntity;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);
	void deleteUserByUserId(String userId);
	void patchUser(UserRegisterPostReq registerInfo, String userId);

    EstateInfo getEstateInfoByResgistrationNumber(String registrationNumber);
}
