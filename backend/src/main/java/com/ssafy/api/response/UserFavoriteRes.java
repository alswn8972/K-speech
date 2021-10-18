package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ResidenceInfo;
import com.ssafy.db.entity.UserFavorite;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 관심 매물 찜하기 조회 API ([GET] /api/v1/favorites) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserFavoriteRes")
public class UserFavoriteRes extends BaseResponseBody {
	List<UserFavorite> userFavoriteList = new ArrayList<>();

	public static UserFavoriteRes of(List<UserFavorite> userFavorites) {
		UserFavoriteRes res = new UserFavoriteRes();
		res.setStatusCode(200);
		res.setMessage("success");
		res.setUserFavoriteList(userFavorites);
		return res;
	}

	public static UserFavoriteRes of(Integer setStatusCode, String message) {
		UserFavoriteRes res = new UserFavoriteRes();
		res.setStatusCode(setStatusCode);
		res.setMessage(message);
		return res;
	}

}
