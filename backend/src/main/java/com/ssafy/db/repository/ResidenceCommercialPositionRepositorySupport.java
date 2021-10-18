package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QResidenceCommercialCount;
import com.ssafy.db.entity.QResidenceCommercialPosition;
import com.ssafy.db.entity.ResidenceCommercialCount;
import com.ssafy.db.entity.ResidenceCommercialPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 매물 근처 검색 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ResidenceCommercialPositionRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QResidenceCommercialPosition qResidenceCommercialPosition = QResidenceCommercialPosition.residenceCommercialPosition;

    public List<ResidenceCommercialPosition> findCommercialPositionByResidenceId(long residenceId) {
        JPAQuery<ResidenceCommercialPosition> residenceCommercialPosition = jpaQueryFactory.select(qResidenceCommercialPosition).from(qResidenceCommercialPosition)
                .where(qResidenceCommercialPosition.residenceInfo.id.eq(residenceId));
        return residenceCommercialPosition.fetch();

    }
}
