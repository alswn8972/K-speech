package com.kspeech.game.api.service;

import com.kspeech.game.db.entity.*;
import com.kspeech.game.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private WordRedisRepository wordRedisRepository;
    @Autowired
    private SentenceRedisRepository sentenceRedisRepository;
    @Autowired
    private WordRankRepository wordRankRedisRepository;
    @Autowired
    private SentenceRankRepository sentenceRankRepository;
    @Autowired
    private RainRankRepository rainRankRepository;
    @Autowired
    private RockRankRepository rockRankRepository;


    @Override
    public void addWord(String content, String pron, int level) {
        wordRedisRepository.save(Word.builder()
                .content(content)
                .pron(pron)
                .level(level)
                .build());
    }
    @Override
    public void addSentence(String content, String pron, int level) {
        sentenceRedisRepository.save(Sentence.builder()
                .content(content)
                .pron(pron)
                .level(level)
                .build());
    }

    @Override
    public void addWordRank(WordRank wr) {
        wordRankRedisRepository.save(wr);
    }

    @Override
    public void addSentenceRank(SentenceRank sr) {
        sentenceRankRepository.save(sr);
    }

    @Override
    public void addRainRank(RainRank rr) {
        rainRankRepository.save(rr);
    }

    @Override
    public void addRockRank(RockRank rr) {
        rockRankRepository.save(rr);
    }
    @Override
    public List<Word> getWord() { return (List<Word>) wordRedisRepository.findAll(); }
    @Override
    public List<Sentence> getSentence() { return (List<Sentence>) sentenceRedisRepository.findAll(); }

    @Override
    public Map<String, Object> getRank() {
        List<Rank> res = new ArrayList<>();

        List<WordRank> w = (List<WordRank>) wordRankRedisRepository.findAll();
        List<SentenceRank> s = (List<SentenceRank>) sentenceRankRepository.findAll();
        List<RainRank> a = (List<RainRank>) rainRankRepository.findAll();
        List<RockRank> b = (List<RockRank>) rockRankRepository.findAll();

        w.sort((arg0, arg1) -> {
            // TODO Auto-generated method stub
            int age0 = arg0.getScore();
            int age1 = arg1.getScore();

            if(age0 == age1) return 0;
            else if(age0 > age1) return -1;
            else return 1;
        });

        s.sort((arg0, arg1) -> {
            // TODO Auto-generated method stub
            int age0 = arg0.getScore();
            int age1 = arg1.getScore();

            if(age0 == age1) return 0;
            else if(age0 > age1) return -1;
            else return 1;
        });

        a.sort((arg0, arg1) -> {
            // TODO Auto-generated method stub
            int age0 = arg0.getScore();
            int age1 = arg1.getScore();

            if(age0 == age1) return 0;
            else if(age0 > age1) return -1;
            else return 1;
        });

        b.sort((arg0, arg1) -> {
            // TODO Auto-generated method stub
            int age0 = arg0.getScore();
            int age1 = arg1.getScore();

            if(age0 == age1) return 0;
            else if(age0 > age1) return -1;
            else return 1;
        });


        Map<String, Object> map = new HashMap<>();

        map.put("WordRank", w);
        map.put("SentenceRank", s);
        map.put("RainRank", a);
        map.put("RockRank", b);

        return map;


    }

}