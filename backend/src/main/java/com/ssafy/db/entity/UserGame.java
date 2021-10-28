package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserGame extends BaseEntity {

//    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
//    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    @JoinColumn(name = "game_one_id")
//    private GameOne gameOne;
//    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    @JoinColumn(name = "game_tow_id")
//    private GameTwo gameTwo;
//    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    @JoinColumn(name = "game_three_id")
//    private GameThree gameThree;
//
//    public static UserGame of(User user, GameOne gameOne, GameTwo gameTwo, GameThree gameThree) {
//        UserGame userGame = new UserGame();
//        userGame.setUser(user);
//        userGame.setGameOne(gameOne);
//        userGame.setGameTwo(gameTwo);
//        userGame.setGameThree(gameThree);
//        return userGame;
//    }
}
