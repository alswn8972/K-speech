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
@ApiModel("WordListResponse")
public class WordListRes {
    List<WordRes> word;

    public static WordListRes of(Optional<List<Word>> word){
        WordListRes res=new WordListRes();
        List<WordRes> list=getWordRes(word);
        res.setWord(list);
        return res;
    }
    private static List<WordRes> getWordRes(Optional<List<Word>> gameWord) {
        List<WordRes> res = new ArrayList<WordRes>();
        for(Word w : gameWord.get()) {
            WordRes wr = new WordRes();
            wr.setContent(w.getContent());
            wr.setPron(w.getPron());
            res.add(wr);
        }
        return res;
    }
}
