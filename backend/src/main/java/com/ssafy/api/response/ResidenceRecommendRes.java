package com.ssafy.api.response;

import com.ssafy.api.model.RecommendModel;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 매물 추천 조회 API ([GET] /api/v1/search/rooms) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ResidenceRecommendRes")
public class ResidenceRecommendRes extends BaseResponseBody {
	List<RecommendModel> recommendModelList = new ArrayList<>();

	public static ResidenceRecommendRes of(List<RecommendModel> recommendModels) {
		ResidenceRecommendRes res = new ResidenceRecommendRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setRecommendModelList(recommendModels);
		return res;
	}

	public static ResidenceRecommendRes of(Integer setStatusCode, String message) {
		ResidenceRecommendRes res = new ResidenceRecommendRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
