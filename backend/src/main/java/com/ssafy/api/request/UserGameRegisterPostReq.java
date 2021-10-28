package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * 유저 게임결과 API ([POST] /api/users/result) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserGameRegisterPostReq")
public class UserGameRegisterPostReq {
    @ApiModelProperty(name="유저 ID", example="ssafy_web")
    String userId;
    @ApiModelProperty(name="게임한 날짜, 시간", example="YYYY-MM-DD hh:mm:ss")
    Date date;
    @ApiModelProperty(name="게임 레벨", example="1")
    String level;
    @ApiModelProperty(name="게임 점수", example="3")
    String score;
    @ApiModelProperty(name="게임 종류", example="1")
    String type;
}