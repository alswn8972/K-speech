package com.kspeech.game.api.service;

import com.kspeech.game.db.entity.Sentence;
import com.kspeech.game.db.entity.Word;

import java.util.List;

public interface RedisService {
    //void setGameWord();
    void addWord(String content, String pron, int level);
    void addSentence(String content, String pron, int level);
    List<Word> getWord();
    List<Sentence> getSentence();
}
