package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getUserId());
		user.setUserPass(passwordEncoder.encode(userRegisterInfo.getUserPass()));
		user.setUserNickName(userRegisterInfo.getUserNickName());

		return userRepository.save(user);
	}

	@Override
	public void patchUser(UserRegisterPostReq userRegisterInfo, String userId) {
		Optional<User> user = userRepository.findById(userRepositorySupport.findUserByUserId(userId).get().getId());
		//user.get().setUserEmail(userRegisterInfo.getUserEmail());
		//user.get().setUserPass(passwordEncoder.encode(userRegisterInfo.getUserPass()));
		user.get().setUserNickName(userRegisterInfo.getUserNickName());
		userRepository.save(user.get());
	}



	@Override
	public User getUserByUserId(String userId) {
		return userRepositorySupport.findUserByUserId(userId).get();
	}

	@Override
	public User getUserByUserNickName(String userNickName) {
		return userRepositorySupport.findUserByUserNickName(userNickName).get();
	}

	@Override
	public void deleteUserByUserId(String userId) {
		userRepository.deleteById(userRepositorySupport.findUserByUserId(userId).get().getId());
	}
}
