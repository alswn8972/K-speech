package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 질문 옵션 API ([POST] /api/v1/questions/options) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("QuestionOptionPostReq")
public class QuestionOptionPostReq {
	@ApiModelProperty(name="질문 ID", example="1")
	Long questionId;
	@ApiModelProperty(name="상세 질문 내용", example="강서구")
	String optionContent;
}

