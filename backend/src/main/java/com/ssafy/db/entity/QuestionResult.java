package com.ssafy.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 질문 모델 정의.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class QuestionResult extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    int result;
    int resultIndex;
}