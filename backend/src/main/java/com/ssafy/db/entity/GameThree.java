package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class GameThree extends BaseEntity{
    @OneToMany(mappedBy="gameThree")
    private List<UserGame> userConcertList=new ArrayList<>();

    private String level;
    private String score;
    private Date date;
}
