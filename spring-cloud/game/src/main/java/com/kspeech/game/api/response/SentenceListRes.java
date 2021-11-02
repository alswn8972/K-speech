package com.kspeech.game.api.response;

import com.kspeech.game.db.entity.Sentence;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("WordListResponse")
public class SentenceListRes {
    List<SentenceRes> sentence;

    public static SentenceListRes of(List<Sentence> sentence){
        SentenceListRes res=new SentenceListRes();
        List<SentenceRes> list=getSentenceRes(sentence);
        res.setSentence(list);
        return res;
    }
    private static List<SentenceRes> getSentenceRes(List<Sentence> gameSentence) {
        List<SentenceRes> res = new ArrayList<SentenceRes>();
        for(Sentence s : gameSentence) {
            SentenceRes sr = new SentenceRes();
            sr.setContent(s.getContent());
            sr.setPron(s.getPron());
            sr.setLevel(s.getLevel());
            res.add(sr);
        }
        return res;
    }
}