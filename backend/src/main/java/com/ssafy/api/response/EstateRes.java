package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.EstateInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 부동산 API ([GET] /api/v1/users/estate) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("EstateRes")
public class EstateRes extends BaseResponseBody {
	@ApiModelProperty(name="Estate")
	EstateInfo estateInfo;
	
	public static EstateRes of(EstateInfo estate) {
		EstateRes res = new EstateRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setEstateInfo(estate);
		return res;
	}

	public static EstateRes of(Integer setStatusCode, String message) {
		EstateRes res = new EstateRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
