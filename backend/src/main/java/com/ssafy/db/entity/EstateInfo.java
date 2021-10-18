package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 부동산 정보 모델 정의.
 */
@Entity
@Getter
@Setter
public class EstateInfo extends BaseEntity{
    String registrationNumber;
    String name;
    String phone;
    String address;
    String representative;
}