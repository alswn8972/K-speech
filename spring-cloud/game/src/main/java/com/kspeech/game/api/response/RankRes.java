package com.kspeech.game.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel("RankResponse")
public class RankRes {
    String userId;
    String score;
    int level;
    int type;
}
