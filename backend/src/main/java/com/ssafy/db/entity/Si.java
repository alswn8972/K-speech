package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 시 모델 정의.
 */
@Entity
@Getter
@Setter
public class Si extends BaseEntity{
    String siName;
}