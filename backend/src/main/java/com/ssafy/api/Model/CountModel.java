package com.ssafy.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CountModel {
    String name;
    double lat;
    double lon;
    long count;
}
