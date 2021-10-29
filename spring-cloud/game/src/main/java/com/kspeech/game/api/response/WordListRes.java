package com.kspeech.game.api.response;

import com.kspeech.game.db.entity.Word;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ApiModel("WordListResponse")
public class WordListRes {
    List<WordRes> word;

    public static WordListRes of(List<Word> word){
        WordListRes res=new WordListRes();
        List<WordRes> list=getWordRes(word);
        res.setWord(list);
        return res;
    }
    private static List<WordRes> getWordRes(List<Word> gameWord) {
        List<WordRes> res = new ArrayList<WordRes>();
        for(Word w : gameWord) {
            WordRes wr = new WordRes();
            wr.setContent(w.getContent());
            wr.setPron(w.getPron());
            wr.setLevel(w.getLevel());
            res.add(wr);
        }
        return res;
    }
}
