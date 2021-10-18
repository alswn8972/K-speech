package com.ssafy.db.repository;

import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.QuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 질문 대답 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface QuestionResultRepository extends JpaRepository<QuestionResult, Long> {
}