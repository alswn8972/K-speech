package com.ssafy.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class QnAModel {
    Long index;
    String question;
    List<String> answer = new ArrayList<>();
}
