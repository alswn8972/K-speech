package com.ssafy.api.request;

import com.ssafy.db.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * 방 검색 ([Get] /api/v1/search/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@Data
@ToString
public class ResidencePostReq {
//	@ApiModelProperty(name="매물 거래 타입", example="1")
	Long residenceType;
//	@ApiModelProperty(name="부동산 등록 번호", example="1")
	Long estateId;
//	@ApiModelProperty(name="매물 타입", example="원룸")
	Long residenceCategory;
//	@ApiModelProperty(name="매물 동 이름", example="가양동")
	String dong;
//	@ApiModelProperty(name="매물 툭징", example="주차가능")
	List<String> feature;
//	@ApiModelProperty(name="건물 이름", example="삼성래미안")
	String name;
//	@ApiModelProperty(name="위도", example="10")
	String lat;
//	@ApiModelProperty(name="경도", example="40")
	String lon;
//	@ApiModelProperty(name="매매가", example="14500")
	int cost;
//	@ApiModelProperty(name="월세비", example="100")
	int wolseCost;
//	@ApiModelProperty(name="보증금", example="100")
	int deposit;
//	@ApiModelProperty(name="전세비 or 보증금", example="0")
	int jeonseCost;
//	@ApiModelProperty(name="관리비", example="20")
	double manageCost;
//	@ApiModelProperty(name="질문 ID", example="1")
	int area;
//	@ApiModelProperty(name="매물 층수", example="3")
	String myFloor;
//	@ApiModelProperty(name="구조", example="분리형")
	String structure;
//	@ApiModelProperty(name="건물 층수", example="5")
	String buildingFloor;
//	@ApiModelProperty(name="방향", example="6")
	String direction;
	List<MultipartFile> thumbnails;
}