package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.Si;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 시 목록 조회 API ([GET] /api/v1/search/si) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("SiResponse")
public class SiRes extends BaseResponseBody {
	@ApiModelProperty(name="Si List")
	List<Si> siList = new ArrayList<>();
	
	public static SiRes of(List<Si> sis) {
		SiRes res = new SiRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setSiList(sis);
		return res;
	}

	public static SiRes of(Integer setStatusCode, String message) {
		SiRes res = new SiRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
