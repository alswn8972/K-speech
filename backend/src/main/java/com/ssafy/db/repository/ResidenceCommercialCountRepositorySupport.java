package com.ssafy.db.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.model.PositionModel;
import com.ssafy.api.model.ResidencePaging;
import com.ssafy.api.request.*;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 매물 근처 검색 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ResidenceCommercialCountRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QResidenceCommercialCount qResidenceCommercialCount = QResidenceCommercialCount.residenceCommercialCount;

    public List<ResidenceCommercialCount> findResidenceCommercialCountByResidenceId(long residenceId) {
        JPAQuery<ResidenceCommercialCount> residenceCommercialCount = jpaQueryFactory.select(qResidenceCommercialCount).from(qResidenceCommercialCount)
                .where(qResidenceCommercialCount.residenceInfo.id.eq(residenceId));
        return residenceCommercialCount.fetch();
    }
}
