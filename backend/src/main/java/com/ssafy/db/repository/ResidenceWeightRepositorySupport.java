package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.CommercialCount;
import com.ssafy.db.entity.QCommercialCount;
import com.ssafy.db.entity.QResidenceWeight;
import com.ssafy.db.entity.ResidenceWeight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 동 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ResidenceWeightRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QResidenceWeight qResidenceWeight= QResidenceWeight.residenceWeight;


    public List<ResidenceWeight> findWeightsByResidenceId(Long residenceId) {
        List<ResidenceWeight> residenceWeight = jpaQueryFactory.select(qResidenceWeight).from(qResidenceWeight)
                .where(qResidenceWeight.residenceInfo.id.eq(residenceId)).fetch();
        return residenceWeight;
    }
}
