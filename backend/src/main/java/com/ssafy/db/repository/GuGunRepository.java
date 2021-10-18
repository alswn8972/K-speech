package com.ssafy.db.repository;

import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 구/군 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface GuGunRepository extends JpaRepository<Gugun, Long> {
}