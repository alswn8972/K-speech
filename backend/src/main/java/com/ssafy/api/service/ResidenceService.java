package com.ssafy.api.service;

import com.ssafy.api.model.*;
import com.ssafy.api.request.*;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

/**
 *	매물 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ResidenceService {
	ResidenceSearchPaging getResidenceDetails(ResidenceDetailGetReq residenceDetailGetReq, Authentication authentication);

	ResidenceSearchPaging getResidencesBySiGuDong(ResidenceGetReq residenceGetReq, Authentication authentication);

	void deleteResidence(Long residenceId);

	void createResidence(ResidencePostReq residence) throws IOException;

    List<CountModel> getGetGuCount();

    List<CountModel> getGetDongCount();

	List<PositionModel> getPosition(String dongName);

	ResidenceSearchPaging getResidencesById(ResidenceIdsPostReq residenceIdsPostReq, Authentication authentication);

	ResidencePaging getResidencesByEstateId(ResidenceEstateIdsPostReq residenceId);

	void patchResidence(ResidencePatchReq residence, long residenceId) throws IOException;

	List<CommercialCountModel> getCommercialCount(String dongName);

	List<RecommendModel> getRecommendResidence(ResidenceRecommendPostReq residenceRecommendPostReq, Authentication authentication);

	List<ResidenceCommercialCountModel> getResidenceCommercial(long residenceId);

	List<ResidenceCommercialPositionModel> getResidenceCommercialPosition(long residenceId);
}
