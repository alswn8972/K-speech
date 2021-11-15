package com.kspeech.game.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@Getter @Setter @Data
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Rank")
public class Rank implements Serializable {
    @Id
    private String id;

    private String userNick;
    private int score;
    private int type;
    private int level;

}