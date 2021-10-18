package com.ssafy.api.response;

import com.ssafy.api.model.PositionModel;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 질문 정보 조회 API ([GET] /api/v1/question) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("PositionResponse")
public class PositionRes extends BaseResponseBody {
	List<PositionModel> positionModelList = new ArrayList<>();
	
	public static PositionRes of(List<PositionModel> positions) {
		PositionRes res = new PositionRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setPositionModelList(positions);
		return res;
	}

	public static PositionRes of(Integer setStatusCode, String message) {
		PositionRes res = new PositionRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}
}
