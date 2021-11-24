package com.kspeech.game.api.service;

import com.kspeech.game.db.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RedisService {
    //void setGameWord();
    void addWord(String content, String pron, int level);
    void addSentence(String content, String pron, int level);

    void addWordRank(WordRank wr);
    void addSentenceRank(SentenceRank sr);
    void addRainRank(RainRank rr);
    void addRockRank(RockRank rr);

    List<Word> getWord();
    List<Sentence> getSentence();
    Map<String, Object> getRank();
}
