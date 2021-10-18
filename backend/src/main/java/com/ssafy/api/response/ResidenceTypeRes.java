package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ResidenceType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 거래 타입 조회 API ([GET] /api/v1/search/bargaintypes) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ResidenceTypeRes")
public class ResidenceTypeRes extends BaseResponseBody {
	List<ResidenceType> residenceTypeList = new ArrayList<>();

	public static ResidenceTypeRes of(List<ResidenceType> residenceTypes) {
		ResidenceTypeRes res = new ResidenceTypeRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setResidenceTypeList(residenceTypes);
		return res;
	}

	public static ResidenceTypeRes of(Integer setStatusCode, String message) {
		ResidenceTypeRes res = new ResidenceTypeRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
