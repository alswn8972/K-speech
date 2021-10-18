package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ResidenceCategory;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 방 종류 조회 API ([GET] /api/v1/search/roomtypes) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ResidenceCategoryRes")
public class ResidenceCategoryRes extends BaseResponseBody {
	List<ResidenceCategory> residenceCategoryList = new ArrayList<>();

	public static ResidenceCategoryRes of(List<ResidenceCategory> residenceCategories) {
		ResidenceCategoryRes res = new ResidenceCategoryRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setResidenceCategoryList(residenceCategories);
		return res;
	}

	public static ResidenceCategoryRes of(Integer setStatusCode, String message) {
		ResidenceCategoryRes res = new ResidenceCategoryRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
