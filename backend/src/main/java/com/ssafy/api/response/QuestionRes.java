package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 질문 정보 조회 API ([GET] /api/v1/question) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("QuestionResponse")
public class QuestionRes extends BaseResponseBody {
	@ApiModelProperty(name="Question ID")
	String userId;
	List<Question> questionList = new ArrayList<>();
	
	public static QuestionRes of(List<Question> questions) {
		QuestionRes res = new QuestionRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setQuestionList(questions);
		return res;
	}

	public static QuestionRes of(Integer setStatusCode, String message) {
		QuestionRes res = new QuestionRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
