package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Feature;
import com.ssafy.db.entity.QFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 유저 검색 필터 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class FeatureRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QFeature qFeature = QFeature.feature;

    public Optional<Feature> findFeatureByFeatureName(String name) {
        Feature feature = jpaQueryFactory.select(qFeature).from(qFeature)
                .where(qFeature.featureName.eq(name)).fetchOne();
        if(feature == null) return Optional.empty();
        return Optional.ofNullable(feature);
    }
}