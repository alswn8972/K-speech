package com.ssafy.api.service;

import com.ssafy.api.model.DongModel;
import com.ssafy.api.request.ResidenceDetailGetReq;
import com.ssafy.api.request.ResidenceGetReq;
import com.ssafy.db.entity.*;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 *	방 검색 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface SearchService {
	void createResidenceCategory(String type);

	List<ResidenceCategory> getResidenceCategory();

	void deleteResidenceCategory(List<Long> roomType);

	void createResidenceType(String type);

	List<ResidenceType> getResidenceType();

	void deleteResidenceType(List<Long> residenceType);

    List<Si> getSi();

	List<String> getGuGun(String siName);

	List<DongModel> getDong(String guGunName);
}
