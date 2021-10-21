package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//게임 결과 모델 정의

@Entity
@Getter
@Setter
public class GameResult extends BaseEntity{

    Long firstResult;
    Long secondResult;
    Long thirdResult;

}
