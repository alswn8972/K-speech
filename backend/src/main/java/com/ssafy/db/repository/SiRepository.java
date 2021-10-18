package com.ssafy.db.repository;

import com.ssafy.db.entity.QuestionOption;
import com.ssafy.db.entity.Si;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 시 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface SiRepository extends JpaRepository<Si, Long> {
}