package com.ssafy.api.response;

import com.ssafy.api.model.ResidenceCommercialCountModel;
import com.ssafy.api.model.ResidenceCommercialPositionModel;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 방 검색 조회 API ([GET] /api/v1/search/rooms) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ResidenceCommercialPositionRes")
public class ResidenceCommercialPositionRes extends BaseResponseBody {
	List<ResidenceCommercialPositionModel> residenceCommercialPositionModel = new ArrayList<>();

	public static ResidenceCommercialPositionRes of(List<ResidenceCommercialPositionModel> residenceCommercialPositionModel) {
		ResidenceCommercialPositionRes res = new ResidenceCommercialPositionRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setResidenceCommercialPositionModel(residenceCommercialPositionModel);
		return res;
	}

	public static ResidenceCommercialPositionRes of(Integer setStatusCode, String message) {
		ResidenceCommercialPositionRes res = new ResidenceCommercialPositionRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
