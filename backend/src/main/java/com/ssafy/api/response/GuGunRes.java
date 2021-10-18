package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Gugun;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 구군 목록 조회 API ([GET] /api/v1/search/gugun) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("GuGunResponse")
public class GuGunRes extends BaseResponseBody {
	@ApiModelProperty(name="GuGun List")
	List<String> guGunListList = new ArrayList<>();
	
	public static GuGunRes of(List<String> guGuns) {
		GuGunRes res = new GuGunRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setGuGunListList(guGuns);
		return res;
	}

	public static GuGunRes of(Integer setStatusCode, String message) {
		GuGunRes res = new GuGunRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
