package com.ssafy.api.redis.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@RedisHash("wordModels")
public class Word implements Serializable {
    @Id
    private String id;

    private String content;
    private String pron;
    private int level;

    @Builder
    public Word(String content, String pron, int level){
        this.content=content;
        this.pron=pron;
        this.level=level;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", pron='" + pron + '\'' +
                ", level=" + level +
                '}';
    }
}
