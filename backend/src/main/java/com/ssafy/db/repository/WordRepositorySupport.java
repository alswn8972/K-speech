package com.ssafy.db.repository;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.QWord;
import com.ssafy.db.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WordRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QWord qWord = QWord.word;

    public Optional<List<Word>> findWord(){
        List<Word> word = jpaQueryFactory.select(qWord).from(qWord).limit(15).fetch();
        return Optional.ofNullable(word);
    }
}
