package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 구, 군 모델 정의.
 */
@Entity
@Getter
@Setter
public class Gugun extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private Si Si;

    String gugunName;
    double lon;
    double lat;
}