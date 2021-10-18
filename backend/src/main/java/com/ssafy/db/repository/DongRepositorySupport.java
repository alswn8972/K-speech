package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.QDong;
import com.ssafy.db.entity.QResidenceType;
import com.ssafy.db.entity.ResidenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 동 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class DongRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QDong qDong = QDong.dong;

    public List<Dong> getDongByGuGunName(String guGunName) {
        List<Dong> dongs = jpaQueryFactory.select(qDong).from(qDong)
                .where(qDong.Gugun.gugunName.eq(guGunName)).orderBy(qDong.dongName.asc()).fetch();
        return dongs;
    }

    public Dong findDongByName(String dongName) {
        Dong dong = jpaQueryFactory.select(qDong).from(qDong)
                .where(qDong.dongName.eq(dongName)).fetchOne();
        return dong;
    }
}
