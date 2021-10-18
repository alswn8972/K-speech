package com.ssafy.api.service;

import com.ssafy.api.model.QnAModel;
import com.ssafy.api.request.QuestionOptionPostReq;
import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.QuestionOption;
import com.ssafy.db.repository.QuestionOptionRepository;
import com.ssafy.db.repository.QuestionOptionRepositorySupport;
import com.ssafy.db.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *	질문 옵션 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("questionOptionService")
public class QuestionOptionServiceImpl implements QuestionOptionService {

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionOptionRepository questionOptionRepository;

	@Autowired
	QuestionOptionRepositorySupport questionOptionRepositorySupport;

	@Override
	public void createQuestionOption(QuestionOptionPostReq questionOptionInfo) {
		QuestionOption questionOption = new QuestionOption();
		questionOption.setOptionContent(questionOptionInfo.getOptionContent());
		questionOption.setQuestion(questionRepository.findById(questionOptionInfo.getQuestionId()).get());
		questionOption.setOptionContentIndex(questionOptionRepositorySupport.countQuestionOption(questionOptionInfo.getQuestionId())+1);
		questionOptionRepository.save(questionOption);
	}

	@Override
	public List<QnAModel> getQuestionOption() {
		HashMap<Long,Integer> map = new LinkedHashMap<>();
		List<QnAModel> qnAModelList = new ArrayList<>();
		List<QuestionOption> questionOptionList = questionOptionRepository.findAll();
		List<Question> questionList = questionRepository.findAll();

		for (int i=0; i<questionList.size(); i++) {
			QnAModel qna = new QnAModel();
			qna.setIndex(questionList.get(i).getId());
			qna.setQuestion(questionList.get(i).getQuestionContent());
			qnAModelList.add(qna);
			map.put(questionList.get(i).getId(), i);
		}

		for (QuestionOption questionOption: questionOptionList) {
			int index = map.get(questionOption.getQuestion().getId());
			QnAModel qna = qnAModelList.get(index);
			qna.getAnswer().add(questionOption.getOptionContent());
			qnAModelList.set(index, qna);
		}
		return qnAModelList;
	}
	@Override
	public void deleteQuestionOption(List<Long> questionOptionId) {
		for (Long id:questionOptionId)
			questionOptionRepository.deleteById(id);
	}

	@Override
	public void deleteQuestionOptionByQuestionId(List<Long> questionId) {
		questionOptionRepositorySupport.deleteByQuestionId(questionId);
	}
}
