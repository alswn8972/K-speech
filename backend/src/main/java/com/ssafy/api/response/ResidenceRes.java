package com.ssafy.api.response;

import com.ssafy.api.model.ResidencePaging;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ResidenceInfo;
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
@ApiModel("ResidenceRes")
public class ResidenceRes extends BaseResponseBody {
	List<ResidenceInfo> residenceInfo = new ArrayList<>();
	long pageSize;

	public static ResidenceRes of(ResidencePaging roomRes) {
		ResidenceRes res = new ResidenceRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setResidenceInfo(roomRes.getResidenceInfos());
		res.setPageSize(roomRes.getPageSize());
		return res;
	}

	public static ResidenceRes of(Integer setStatusCode, String message) {
		ResidenceRes res = new ResidenceRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
