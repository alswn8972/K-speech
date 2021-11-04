package com.kspeech.game.api.response;

import com.kspeech.game.db.entity.Rank;
import com.kspeech.game.db.entity.Sentence;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("RankListResponse")
public class RankListRes {
    List<RankRes> rank;


    public static RankListRes of(List<Rank> rank){
        RankListRes res=new RankListRes();
        List<RankRes> list=getRankRes(rank);
        res.setRank(list);
        return res;
    }
    private static List<RankRes> getRankRes(List<Rank> rankList) {
        List<RankRes> res = new ArrayList<>();
        for(Rank r : rankList) {
            RankRes rr = new RankRes();
            rr.setUserId(r.getUserId());
            rr.setScore(r.getScore());
            rr.setLevel(r.getLevel());
            rr.setType(r.getType());
            res.add(rr);
        }
        return res;
    }
}