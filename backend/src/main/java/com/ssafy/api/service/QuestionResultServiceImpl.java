package com.ssafy.api.service;

import com.ssafy.api.request.QuestionResultPostReq;
import com.ssafy.common.auth.UserDetail;
import com.ssafy.db.entity.QuestionResult;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.QuestionResultRepository;
import com.ssafy.db.repository.QuestionResultRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 *	질문 답변 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("questionResultService")
public class QuestionResultServiceImpl implements QuestionResultService {
	@Autowired
	QuestionResultRepository questionResultRepository;

	@Autowired
	QuestionResultRepositorySupport questionResultRepositorySupport;

	@Autowired
	QuestionResultService questionResultService;

	@Autowired
	UserService userService;

	@Override
	public void createQuestionResult(Authentication authentication, QuestionResultPostReq questionResultPostReq) {
		UserDetail userDetail = (UserDetail) authentication.getDetails();
		User user = userService.getUserByUserId(userDetail.getUsername());

		try{
			QuestionResult questionResult = questionResultRepositorySupport.findQuestionResultByUesrIdAndResult(questionResultPostReq.getResultIndex(), user);
			saveResult(questionResult, questionResultPostReq, user);
		}catch (Exception e) {
			QuestionResult questionResult = new QuestionResult();
			saveResult(questionResult, questionResultPostReq, user);
		}
	}

	private void saveResult(QuestionResult questionResult, QuestionResultPostReq questionResultPostReq, User user) {
		questionResult.setResult(questionResultPostReq.getResult());
		questionResult.setResultIndex(questionResultPostReq.getResultIndex());
		questionResult.setUser(user);
		questionResultRepository.save(questionResult);
	}
}