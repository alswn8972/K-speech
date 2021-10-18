package com.ssafy.api.model;

import com.ssafy.db.entity.ResidenceInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class ResidencePaging {
    List<ResidenceInfo> residenceInfos;
    long pageSize;
}
