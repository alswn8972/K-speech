package com.ssafy.db.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.ResidenceDetailGetReq;
import com.ssafy.api.request.ResidenceGetReq;
import com.ssafy.db.entity.QResidenceCategory;
import com.ssafy.db.entity.QResidenceInfo;
import com.ssafy.db.entity.ResidenceCategory;
import com.ssafy.db.entity.ResidenceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 방 종류 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ResidenceCategoryRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QResidenceCategory qResidenceCategory = QResidenceCategory.residenceCategory;

    public ResidenceCategory getResidenceCategoryByCategory(String s) {
        ResidenceCategory residenceCategory = jpaQueryFactory.select(qResidenceCategory).from(qResidenceCategory)
                .where(qResidenceCategory.categoryName.eq(s)).fetchOne();
        return residenceCategory;
    }
}
