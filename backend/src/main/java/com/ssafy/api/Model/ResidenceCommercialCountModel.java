package com.ssafy.api.model;

import com.ssafy.db.entity.ResidenceInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ResidenceCommercialCountModel {
    long residenceId;
    long cafeCount;
    long healthCount;
    long convenienceCount;
    long busCount;
    long bicycleCount;
    long subwayCount;
}
