package com.ssafy.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 매물 모델 정의.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class ResidenceInfo extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ResidenceType residenceType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EstateInfo estateInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ResidenceCategory residenceCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Dong dong;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ImageUrl> imageUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Feature> feature;

    String name;
    String lat;
    String lon;
    int cost;
    int wolseCost;
    int jeonseCost;
    int deposit;
    double manageCost;
    int area;
    String content;
    String myFloor; // 층
    String structure; // 방 구조
    String buildingFloor; // 층
    String direction;
    long favoriteCnt;
    boolean sale;
}