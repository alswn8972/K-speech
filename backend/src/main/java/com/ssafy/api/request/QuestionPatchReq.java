package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 질문 수정 API ([Patch] /api/v1/questions) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("QuestionPatchReq")
public class QuestionPatchReq {
	@ApiModelProperty(name="질문 ID", example="1")
	Long questionId;
	@ApiModelProperty(name="질문 내용", example="어디에 살고 싶나요?")
	String questionContent;

}

