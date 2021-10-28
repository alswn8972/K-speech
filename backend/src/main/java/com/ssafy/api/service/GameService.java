package com.ssafy.api.service;

import com.ssafy.db.entity.Word;

import java.util.List;
import java.util.Optional;

public interface GameService {
    Optional<List<Word>> getWord();
}
