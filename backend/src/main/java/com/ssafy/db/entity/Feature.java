package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 매물 특징 모델 정의.
 */
@Entity
@Getter
@Setter
public class Feature extends BaseEntity {
    String featureName;
}