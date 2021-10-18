package com.ssafy.api.response;

import com.ssafy.api.model.CountModel;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ResidenceInfo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 방 검색 조회 API ([GET] /api/v1/residences/count) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("CountRes")
public class CountRes extends BaseResponseBody {
	List<CountModel> counts = new ArrayList<>();

	public static CountRes of(List<CountModel> countModelList) {
		CountRes res = new CountRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setCounts(countModelList);
		return res;
	}

	public static CountRes of(Integer setStatusCode, String message) {
		CountRes res = new CountRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
