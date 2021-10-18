package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 동 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class CommercialCountRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCommercialCount qCommercialCount= QCommercialCount.commercialCount;

    public List<CommercialCount> findCommercialCountByDongName(String dongName) {
        List<CommercialCount> commercialCount = jpaQueryFactory.select(qCommercialCount).from(qCommercialCount)
                .where(qCommercialCount.dong.dongName.eq(dongName)).fetch();
        return commercialCount;
    }
}
