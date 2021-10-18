package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 방 검색 ([Get] /api/v1/search/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ResidenceRecommendPostReq")
public class ResidenceRecommendPostReq {
	@ApiModelProperty(name="동 ID", example="1")
	List<Long> dong;
	@ApiModelProperty(name="매물 카테고리", example="1")
	List<Long> resiCategory;
	@ApiModelProperty(name="매물 타입", example="1")
	Long resiType;
	@ApiModelProperty(name="시작 가격(월세, 전세, 매매)", example="0")
	int resiCostStart;
	@ApiModelProperty(name="마지막 가격(월세, 전세, 매매)", example="0")
	int resiCostEnd;
	@ApiModelProperty(name="시작 보증금", example="0")
	int resiDepositStart;
	@ApiModelProperty(name="최대 보증금", example="0")
	int resiDepositEnd;
	@ApiModelProperty(name="상권 가중치", example="0")
	List<Double> score;

}
