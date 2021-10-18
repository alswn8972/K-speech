package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 유저 찜 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserFavoriteRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUserFavorite quserFavorite = QUserFavorite.userFavorite;

    public List<UserFavorite> findByUserId(Long id) {
        List<UserFavorite> userFavorites = jpaQueryFactory.select(quserFavorite).from(quserFavorite)
                .where(quserFavorite.user.id.eq(id)).fetch();
        return userFavorites;
    }

    public Boolean checkIsFavorite(Long userId, Long residenceId) {
        UserFavorite userFavorite = jpaQueryFactory.select(quserFavorite).from(quserFavorite)
                .where(quserFavorite.user.id.eq(userId), quserFavorite.residenceInfo.id.eq(residenceId)).fetchOne();
        if(userFavorite == null) return false;
        return true;
    }

    @Transactional
    public void deleteByIds(Long residenceId, Long userId) {
        jpaQueryFactory.delete(quserFavorite).where(quserFavorite.user.id.eq(userId), quserFavorite.residenceInfo.id.eq(residenceId)).execute();
    }
}