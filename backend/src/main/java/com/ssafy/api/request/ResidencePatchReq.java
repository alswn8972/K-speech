package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 방 검색 ([Get] /api/v1/search/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@Data
@ToString
@ApiModel("ResidencePatchReq")
public class ResidencePatchReq {
	@ApiModelProperty(name="매물 id", example="1")
	Long residenceId;
	@ApiModelProperty(name="매물 툭징", example="주차가능")
	List<String> feature;
	@ApiModelProperty(name="건물 이름", example="삼성래미안")
	String name;
	@ApiModelProperty(name="매매가", example="14500")
	int cost;
	@ApiModelProperty(name="월세비", example="100")
	int wolseCost;
	@ApiModelProperty(name="보증금", example="100")
	int deposit;
	@ApiModelProperty(name="전세비 or 보증금", example="0")
	int jeonseCost;
	@ApiModelProperty(name="관리비", example="20")
	double manageCost;
}