package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 매물별 근처 상권 좌표 카테고리 모델 정의.
 */
@Entity
@Getter
@Setter
public class ResidenceCommercialPosition extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ResidenceInfo residenceInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CommercialInfo commercialInfo;
}