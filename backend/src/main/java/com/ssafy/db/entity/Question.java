package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 질문 모델 정의.
 */
@Entity
@Getter
@Setter
public class Question extends BaseEntity{
    String questionContent;
}