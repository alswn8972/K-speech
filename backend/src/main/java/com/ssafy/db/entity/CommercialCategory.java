package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 상권 카테고리 모델 정의.
 */
@Entity
@Getter
@Setter
public class CommercialCategory extends BaseEntity{
    String categoryName;
}