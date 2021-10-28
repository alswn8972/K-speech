package com.ssafy.api.response;

import com.ssafy.db.entity.Word;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ApiModel("WordResponse")
public class WordRes {
    String content;
    String pron;
}
