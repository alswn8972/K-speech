package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	@ApiModelProperty(name="유저 ID", example="ssafy_web")
	String userId;
	@ApiModelProperty(name="유저 Password", example="your_password")
	String userPass;
	@ApiModelProperty(name="유저 Name", example="김싸피")
	String userName;
	@ApiModelProperty(name="유저 Email", example="abc@ssafy.com")
	String userEmail;
	@ApiModelProperty(name="유저 Phone", example="010-1234-5678")
	String userPhone;
	@ApiModelProperty(name="부동산 id", example="1",required = false)
	Long estateId;

}

