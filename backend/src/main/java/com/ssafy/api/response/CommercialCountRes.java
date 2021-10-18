package com.ssafy.api.response;

import com.ssafy.api.model.CommercialCountModel;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 주변 상권 개수조회 API ([GET] /api/v1/residences/count) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("CommercialCountRes")
public class CommercialCountRes extends BaseResponseBody {
	List<CommercialCountModel> commercialCountModelList = new ArrayList<>();

	public static CommercialCountRes of(List<CommercialCountModel> commercialCountModels) {
		CommercialCountRes res = new CommercialCountRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setCommercialCountModelList(commercialCountModels);
		return res;
	}

	public static CommercialCountRes of(Integer setStatusCode, String message) {
		CommercialCountRes res = new CommercialCountRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
