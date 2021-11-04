package com.kspeech.game.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter @Setter
@Builder @ToString
@RedisHash("wordRank")
public class WordRank implements Serializable {
    @Id
    private String id;

    private String userId;
    private String score;
    private int level;
}