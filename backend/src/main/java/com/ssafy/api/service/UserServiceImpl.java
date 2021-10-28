package com.ssafy.api.service;

import com.ssafy.api.request.UserGameRegisterPostReq;
import com.ssafy.db.entity.GameOne;
import com.ssafy.db.entity.GameThree;
import com.ssafy.db.entity.GameTwo;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;

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
	@Autowired
	GameOneRepository gameOneRepository;
	@Autowired
	GameTwoRepository gameTwoRepository;
	@Autowired
	GameThreeRepository gameThreeRepository;


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
	public void createUserGameResult(User user, UserGameRegisterPostReq gameRegisterInfo) {
		if(gameRegisterInfo.getType().equals("1")){
			GameOne gameOne = new GameOne();
			gameOne.setUser(user);
			gameOne.setLevel(gameRegisterInfo.getLevel());
			gameOne.setScore(gameRegisterInfo.getScore());
			gameOne.setDate(gameRegisterInfo.getDate());
			gameOneRepository.save(gameOne);
		}else if(gameRegisterInfo.getType().equals("2")){
			GameTwo gameTwo = new GameTwo();
			gameTwo.setUser(user);
			gameTwo.setLevel(gameRegisterInfo.getLevel());
			gameTwo.setScore(gameRegisterInfo.getScore());
			gameTwo.setDate(gameRegisterInfo.getDate());
			gameTwoRepository.save(gameTwo);
		}else{
			GameThree gameThree = new GameThree();
			gameThree.setUser(user);
			gameThree.setLevel(gameRegisterInfo.getLevel());
			gameThree.setScore(gameRegisterInfo.getScore());
			gameThree.setDate(gameRegisterInfo.getDate());
			gameThreeRepository.save(gameThree);
		}
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
