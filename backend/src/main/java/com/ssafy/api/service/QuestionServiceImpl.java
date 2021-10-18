package com.ssafy.api.service;

import com.ssafy.api.request.QuestionOptionPostReq;
import com.ssafy.api.request.QuestionPatchReq;
import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.QuestionOption;
import com.ssafy.db.repository.QuestionOptionRepository;
import com.ssafy.db.repository.QuestionOptionRepositorySupport;
import com.ssafy.db.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *	질문 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionService questionService;

	@Autowired
	QuestionOptionService questionOptionService;

	@Override
	public void createQuestion(String questionContent) {
		Question question = new Question();
		question.setQuestionContent(questionContent);
		questionRepository.save(question);
	}

	@Override
	public List<Question> getQuestion() {
		return questionRepository.findAll();
	}

	@Override
	public void patchQuestion(QuestionPatchReq questionInfo) {
		Question question = questionRepository.findById(questionInfo.getQuestionId()).get();
		question.setQuestionContent(questionInfo.getQuestionContent());
		questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(List<Long> questionId) {
		for (Long id:questionId){
			questionOptionService.deleteQuestionOptionByQuestionId(questionId);
			questionRepository.deleteById(id);
		}
	}
}