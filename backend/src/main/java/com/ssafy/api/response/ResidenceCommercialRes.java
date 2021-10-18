package com.ssafy.api.response;

import com.ssafy.api.model.ResidenceCommercialCountModel;
import com.ssafy.api.model.ResidenceModel;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ResidenceCommercialCount;
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
@ApiModel("ResidenceDetailRes")
public class ResidenceCommercialRes extends BaseResponseBody {
	List<ResidenceCommercialCountModel> residenceCommercialCountModel = new ArrayList<>();

	public static ResidenceCommercialRes of(List<ResidenceCommercialCountModel> residenceCommercialCountModel) {
		ResidenceCommercialRes res = new ResidenceCommercialRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setResidenceCommercialCountModel(residenceCommercialCountModel);
		return res;
	}

	public static ResidenceCommercialRes of(Integer setStatusCode, String message) {
		ResidenceCommercialRes res = new ResidenceCommercialRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
