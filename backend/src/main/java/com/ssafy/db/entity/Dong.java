package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 동 모델 정의.
 */
@Entity
@Getter
@Setter
public class Dong extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private Gugun Gugun;
    String dongName;
    double lon;
    double lat;
}