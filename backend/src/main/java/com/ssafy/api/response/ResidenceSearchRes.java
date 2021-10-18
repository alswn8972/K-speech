package com.ssafy.api.response;

import com.ssafy.api.model.ResidenceSearchPaging;
import com.ssafy.api.model.ResidenceModel;
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
@ApiModel("ResidenceSearchRes")
public class ResidenceSearchRes extends BaseResponseBody {
	List<ResidenceModel> residenceInfo = new ArrayList<>();
	long pageSize;

	public static ResidenceSearchRes of(ResidenceSearchPaging roomRes) {
		ResidenceSearchRes res = new ResidenceSearchRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setResidenceInfo(roomRes.getResidenceModels());
		res.setPageSize(roomRes.getPageSize());
		return res;
	}

	public static ResidenceSearchRes of(Integer setStatusCode, String message) {
		ResidenceSearchRes res = new ResidenceSearchRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
