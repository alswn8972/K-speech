package com.ssafy.api.model;

import com.ssafy.db.entity.CommercialCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ResidenceCommercialPositionModel {
    String lat;
    String lon;
}
