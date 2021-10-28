package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.GameOne;
import com.ssafy.db.entity.QGameOne;
import com.ssafy.db.entity.QGameThree;
import com.ssafy.db.entity.QGameTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GameRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QGameOne qGameOne = QGameOne.gameOne;
    QGameTwo qGameTwo = QGameTwo.gameTwo;
    QGameThree qGameThree = QGameThree.gameThree;

    //public Optional<GameOne>

}
