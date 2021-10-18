package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QResidenceCategory;
import com.ssafy.db.entity.QResidenceType;
import com.ssafy.db.entity.ResidenceCategory;
import com.ssafy.db.entity.ResidenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 방 거래 타입 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ResidenceTypeRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QResidenceType qResidenceType = QResidenceType.residenceType;

    public ResidenceType getResidenceTypeByTypeName(String s) {
        ResidenceType residenceType = jpaQueryFactory.select(qResidenceType).from(qResidenceType)
                .where(qResidenceType.type.eq(s)).fetchOne();
        return residenceType;
    }
}
