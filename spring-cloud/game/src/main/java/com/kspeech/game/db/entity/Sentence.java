package com.kspeech.game.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter @Setter
@ToString @Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("SentenceModels")
public class Sentence implements Serializable {
    @Id
    private String id;

    private String content;
    private String pron;
    private int level;

}