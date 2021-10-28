package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class GameOne extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;;

    private String level;
    private String score;
    private Date date;
}
