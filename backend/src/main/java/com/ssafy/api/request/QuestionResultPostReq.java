package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 질문 답변 API ([POST] /api/v1/questions/results) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("QuestionResultPostReq")
public class QuestionResultPostReq {
	@ApiModelProperty(name="질문 답변", example="1")
	int result;
	@ApiModelProperty(name="질문 번호", example="1")
	int resultIndex;
}

