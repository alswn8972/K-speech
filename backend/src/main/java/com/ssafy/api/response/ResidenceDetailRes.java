package com.ssafy.api.response;

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
@ApiModel("ResidenceDetailRes")
public class ResidenceDetailRes extends BaseResponseBody {
	List<ResidenceModel> residenceInfo = new ArrayList<>();

	public static ResidenceDetailRes of(List<ResidenceModel> roomRes) {
		ResidenceDetailRes res = new ResidenceDetailRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setResidenceInfo(roomRes);
		return res;
	}

	public static ResidenceDetailRes of(Integer setStatusCode, String message) {
		ResidenceDetailRes res = new ResidenceDetailRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
