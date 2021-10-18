package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 방 id로 검색 ([Get] /api/v1/search/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ResidenceIdsPostReq")
public class ResidenceIdsPostReq {
	@ApiModelProperty(name="부동산 번호", example="1")
	List<Long> residenceIds;
	@ApiModelProperty(name="페이지 번호", example="1")
	long pageNum;
	@ApiModelProperty(name="정렬 기준", example="cost")
	String sortType;
	@ApiModelProperty(name="정렬 방식", example="asc, desc")
	String sortOrder;
}