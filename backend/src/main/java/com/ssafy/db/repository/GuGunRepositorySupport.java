package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.QGugun;
import com.ssafy.db.entity.QResidenceType;
import com.ssafy.db.entity.ResidenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 구군 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class GuGunRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QGugun qGugun = QGugun.gugun;


    public List<Gugun> getGuGunBySiName(String siName) {
        List<Gugun> guguns = jpaQueryFactory.select(qGugun).from(qGugun)
                .where(qGugun.Si.siName.eq(siName)).orderBy(qGugun.gugunName.asc()).fetch();
        return  guguns;
    }
}
