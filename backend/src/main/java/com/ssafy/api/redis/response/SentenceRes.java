package com.ssafy.api.redis.response;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("SentenceResponse")
public class SentenceRes {
    String content;
    String pron;
    int level;
}
