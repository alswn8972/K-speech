package com.ssafy.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class ResidenceSearchPaging {
    List<ResidenceModel> residenceModels;
    long pageSize;
}
