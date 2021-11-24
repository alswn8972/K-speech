package com.ssafy.api.service;

import com.ssafy.db.entity.Word;
import com.ssafy.db.repository.WordRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("gameService")
public class GameServiceImpl implements GameService {
    @Autowired
    WordRepositorySupport wordRepositorySupport;

    @Override
    public Optional<List<Word>> getWord() {
        return wordRepositorySupport.findWord();
    }
}
