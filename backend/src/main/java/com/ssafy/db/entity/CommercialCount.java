package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 동별 상권 개수 카테고리 모델 정의.
 */
@Entity
@Getter
@Setter
public class CommercialCount extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Dong dong;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CommercialCategory commercialCategory;

    long count;
}