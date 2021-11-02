package com.kspeech.game.api.service;

import com.kspeech.game.db.entity.Sentence;
import com.kspeech.game.db.entity.Word;
import com.kspeech.game.db.repository.SentenceRedisRepository;
import com.kspeech.game.db.repository.WordRedisRepository;
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
        wordRedisRepository.save(Word.builder()
                .content(content)
                .pron(pron)
                .level(level)
                .build());
    }
    public void addSentence(String content, String pron, int level) {
        sentenceRedisRepository.save(Sentence.builder()
                .content(content)
                .pron(pron)
                .level(level)
                .build());
    }

    public List<Word> getWord() { return (List<Word>) wordRedisRepository.findAll(); }

    public List<Sentence> getSentence() { return (List<Sentence>) sentenceRedisRepository.findAll(); }
}