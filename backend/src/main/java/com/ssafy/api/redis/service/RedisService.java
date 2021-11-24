package com.ssafy.api.redis.service;

import com.ssafy.api.redis.entity.Sentence;
import com.ssafy.api.redis.entity.Word;

import java.util.List;


public interface RedisService {
    //void setGameWord();
    void addWord(String content, String pron, int level);
    void addSentence(String content, String pron, int level);
    List<Word> getWord();
    List<Sentence> getSentence();
}
