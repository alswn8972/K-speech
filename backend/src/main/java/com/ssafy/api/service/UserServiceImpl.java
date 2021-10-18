package com.ssafy.api.service;

import com.ssafy.db.entity.EstateInfo;
import com.ssafy.db.repository.EstateInfoRepository;
import com.ssafy.db.repository.EstateInfoRepositorySupport;
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

	@Autowired
	EstateInfoRepository estateInfoRepository;

	@Autowired
	EstateInfoRepositorySupport estateInfoRepositorySupport;

	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getUserId());
		user.setUserPass(passwordEncoder.encode(userRegisterInfo.getUserPass()));
		user.setUserEmail(userRegisterInfo.getUserEmail());
		user.setUserPhone(userRegisterInfo.getUserPhone());
		user.setUserName(userRegisterInfo.getUserName());
		if(userRegisterInfo.getEstateId() != null)
			user.setEstateInfo(estateInfoRepository.findById(userRegisterInfo.getEstateId()).get());
		return userRepository.save(user);
	}
	@Override
	public void patchUser(UserRegisterPostReq userRegisterInfo, String userId) {
		Optional<User> user = userRepository.findById(userRepositorySupport.findUserByUserId(userId).get().getId());
		user.get().setUserEmail(userRegisterInfo.getUserEmail());
//		user.get().setUserPass(passwordEncoder.encode(userRegisterInfo.getUserPass()));
		user.get().setUserPhone(userRegisterInfo.getUserPhone());
		user.get().setUserName(userRegisterInfo.getUserName());
		userRepository.save(user.get());
	}

	@Override
	public EstateInfo getEstateInfoByResgistrationNumber(String registrationNumber) {
		return estateInfoRepositorySupport.getEstateInfoByRegistrationNumber(registrationNumber).get();
	}

	@Override
	public User getUserByUserId(String userId) {
		return userRepositorySupport.findUserByUserId(userId).get();
	}
	@Override
	public void deleteUserByUserId(String userId) {
		userRepository.deleteById(userRepositorySupport.findUserByUserId(userId).get().getId());
	}
}
