package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 매물 카테고리 모델 정의.
 */
@Entity
@Getter
@Setter
public class ResidenceCategory extends BaseEntity{
    String categoryName; // 아파트, 원룸
}