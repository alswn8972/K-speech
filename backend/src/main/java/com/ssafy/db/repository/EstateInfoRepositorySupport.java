package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.EstateInfo;
import com.ssafy.db.entity.QEstateInfo;
import com.ssafy.db.entity.QResidenceType;
import com.ssafy.db.entity.ResidenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 방 거래 타입 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class EstateInfoRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QEstateInfo qEstateInfo = QEstateInfo.estateInfo;

    public Optional<EstateInfo> getEstateInfoByRegistrationNumber(String registrationNumber) {
        EstateInfo estateInfo = jpaQueryFactory.select(qEstateInfo).from(qEstateInfo)
                .where(qEstateInfo.registrationNumber.eq(registrationNumber)).fetchOne();
        if(estateInfo == null) return Optional.empty();
        return Optional.ofNullable(estateInfo);
    }

    public Optional<EstateInfo> getEstateInfoByRegistrationId(Long registrationId) {
        EstateInfo estateInfo = jpaQueryFactory.select(qEstateInfo).from(qEstateInfo)
                .where(qEstateInfo.id.eq(registrationId)).fetchOne();
        if(estateInfo == null) return Optional.empty();
        return Optional.ofNullable(estateInfo);
    }
}
