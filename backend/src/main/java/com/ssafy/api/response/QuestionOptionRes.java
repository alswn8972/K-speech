package com.ssafy.api.response;

import com.ssafy.api.model.QnAModel;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 질문 옵션 조회 API ([GET] /api/v1/questions/options) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("QuestionOptionRes")
public class QuestionOptionRes extends BaseResponseBody {
	@ApiModelProperty(name="Question ID")
	List<QnAModel> qna = new ArrayList<>();
	
	public static QuestionOptionRes of(List<QnAModel> questionOptions) {
		QuestionOptionRes res = new QuestionOptionRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setQna(questionOptions);
		return res;
	}

	public static QuestionOptionRes of(Integer setStatusCode, String message) {
		QuestionOptionRes res = new QuestionOptionRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
