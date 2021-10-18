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
public class ResidenceCommercialCount extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private ResidenceInfo residenceInfo;

    long cafeCount;
    long healthCount;
    long convenienceCount;
    long subwayCount;
    long busCount;
    long bicycleCount;
}