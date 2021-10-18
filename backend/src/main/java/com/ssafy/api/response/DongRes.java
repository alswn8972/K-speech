package com.ssafy.api.response;

import com.ssafy.api.model.DongModel;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 동 목록 조회 API ([GET] /api/v1/search/dong) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("DongResponse")
public class DongRes extends BaseResponseBody {
	@ApiModelProperty(name="DongModel List")
	List<DongModel> dongModelList = new ArrayList<>();
	
	public static DongRes of(List<DongModel> dongModels) {
		DongRes res = new DongRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setDongModelList(dongModels);
		return res;
	}

	public static DongRes of(Integer setStatusCode, String message) {
		DongRes res = new DongRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
