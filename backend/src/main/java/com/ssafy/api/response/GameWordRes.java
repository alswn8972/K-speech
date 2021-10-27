package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("GameWordResponse")
public class GameWordRes{
    @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String key;
    List<String> value;

    public static GameWordRes of(String name, List<String> word){
        GameWordRes res = new GameWordRes();
        res.setKey(name);
        res.setValue(word);
        return res;
    }

}
