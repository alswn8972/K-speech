package com.ssafy.api.service;

import com.ssafy.api.request.QuestionResultPostReq;
import org.springframework.security.core.Authentication;

/**
 *	질문 대답 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface QuestionResultService {

    void createQuestionResult(Authentication authentication, QuestionResultPostReq questionResultPostReq);
}
