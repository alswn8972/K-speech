package com.ssafy.api.redis.service;

import com.ssafy.api.redis.entity.Sentence;
import com.ssafy.api.redis.entity.Word;
import com.ssafy.api.redis.repository.SentenceRedisRepository;
import com.ssafy.api.redis.repository.WordRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private WordRedisRepository wordRedisRepository;
    @Autowired
    private SentenceRedisRepository sentenceRedisRepository;

    public void addWord(String content, String pron, int level) {
        wordRedisRepository.save(new Word(content,pron,level));
    }
    public void addSentence(String content, String pron, int level) {
        System.out.println(content+" "+pron+" "+level);
        sentenceRedisRepository.save(new Sentence(content,pron,level));
    }

    public List<Word> getWord() {
        List<Word> arr = (List<Word>) wordRedisRepository.findAll();
        return arr;
    }

    public List<Sentence> getSentence() {
        List<Sentence> arr = (List<Sentence>) sentenceRedisRepository.findAll();
        return arr;
    }
}
