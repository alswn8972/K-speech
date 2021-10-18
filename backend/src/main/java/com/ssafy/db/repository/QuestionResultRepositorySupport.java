package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 질문 답변 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class QuestionResultRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QQuestionResult qQuestionResult = QQuestionResult.questionResult;


    public QuestionResult findQuestionResultByUesrIdAndResult(int resultIndex, User user) {
        QuestionResult questionResult = jpaQueryFactory.select(qQuestionResult).from(qQuestionResult)
                .where(qQuestionResult.resultIndex.eq(resultIndex), qQuestionResult.user.id.eq(user.getId())).fetchOne();
        return questionResult;
    }
}
