package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 방 상세 검색 ([Get] /api/v1/search/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ResidenceDetailGetReq")
public class ResidenceDetailGetReq {
	@ApiModelProperty(name="방 종류", example="원룸, 투룸")
	List<String> residenceCategory = new ArrayList<>();
	@ApiModelProperty(name="거래 타입", example="월세, 전세")
	List<String> residenceType = new ArrayList<>();
	@ApiModelProperty(name="최저 보증금", example="10")
	int startDeposit;
	@ApiModelProperty(name="최고 보증금", example="10")
	int endDeposit;
	@ApiModelProperty(name="최저 매매 가격대(만원)", example="40")
	int startPrice;
	@ApiModelProperty(name="최고 매매 가격대(만원)", example="100")
	int endPrice;
	@ApiModelProperty(name="최저 전세 가격대(만원)", example="40")
	int startJPrice;
	@ApiModelProperty(name="최고 전세 가격대(만원)", example="100")
	int endJPrice;
	@ApiModelProperty(name="최저 월세 가격대(만원)", example="40")
	int startWPrice;
	@ApiModelProperty(name="최고 월세 가격대(만원)", example="100")
	int endWPrice;
	@ApiModelProperty(name="관리비 최저 가격대(만원)", example="5")
	int startManagePrice;
	@ApiModelProperty(name="관리비 최고 가격대(만원)", example="10")
	int endManagePrice;
	@ApiModelProperty(name="최저 면적(평수)", example="8")
	int startArea;
	@ApiModelProperty(name="최고 면적(평수)", example="10")
	int endArea;
	@ApiModelProperty(name="정렬 기준", example="cost")
	String sortType;
	@ApiModelProperty(name="정렬 방식", example="asc, desc")
	String sortOrder;
	@ApiModelProperty(name="동", example="가양동")
	String dong;
	@ApiModelProperty(name="구", example="강서구")
	String gugun;
	@ApiModelProperty(name="층", example="층")
	List<String> floorDetail;
	@ApiModelProperty(name="구조", example="복층")
	List<String> roomStrucDeail;
	@ApiModelProperty(name="페이지 번호", example="1")
	long pageNum;
}