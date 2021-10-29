package com.ssafy.api.redis.response;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("WordResponse")
public class WordRes {
    String content;
    String pron;
    int level;
}
