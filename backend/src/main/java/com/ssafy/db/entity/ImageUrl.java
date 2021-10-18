package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 이미지 모델 정의.
 */
@Entity
@Getter
@Setter
public class ImageUrl extends BaseEntity {
    String url;
}