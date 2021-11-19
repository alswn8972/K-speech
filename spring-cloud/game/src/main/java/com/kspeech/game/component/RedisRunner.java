package com.kspeech.game.component;

import com.kspeech.game.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisRunner implements ApplicationRunner {
    @Autowired
    RedisService redisService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisService.addWord("사랑", "사랑", 1);
        redisService.addWord("낚시꾼", "낙시꾼", 1);
        redisService.addWord("새까맣다", "새까마타", 1);
        redisService.addWord("봄", "봄", 1);
        redisService.addWord("한여름", "한녀름", 1);
        redisService.addWord("늦가을", "늗까을", 1);
        redisService.addWord("겨울", "겨울", 1);
        redisService.addWord("맑음", "말금", 1);
        redisService.addWord("부동산", "부동산", 1);
        redisService.addWord("사기꾼", "사기꾼", 1);
        redisService.addWord("광복절", "광복쩔", 1);
        redisService.addWord("오징어", "오징어", 1);
        redisService.addWord("상처", "상처", 1);
        redisService.addWord("보름달", "보름딸", 1);
        redisService.addWord("선물", "선물", 1);
        redisService.addWord("결혼식", "결혼식", 1);
        redisService.addWord("한숨", "한숨", 1);
        redisService.addWord("회전목마", "훼전몽마", 1);
        redisService.addWord("서울", "서울", 1);
        redisService.addWord("구미", "구미", 1);
        redisService.addWord("진로", "질로", 1);
        redisService.addWord("졸업", "조럽", 1);
        redisService.addWord("취업", "취업", 1);
        redisService.addWord("공원", "공원", 1);
        redisService.addWord("나무판자", "나무판자", 1);
        redisService.addWord("잡초", "잡초", 1);
        redisService.addWord("땅콩", "땅콩", 1);
        redisService.addWord("딱지", "딱찌", 1);
        redisService.addWord("천연두", "처년두", 1);
        redisService.addWord("깐부", "깐부", 1);
        redisService.addWord("오리", "오리", 1);
        redisService.addWord("초밥", "초밥", 1);
        redisService.addWord("반창고", "반창고", 1);
        redisService.addWord("의사", "의사", 1);
        redisService.addWord("간호사", "간호사", 1);
        redisService.addWord("대법원", "대버붠", 1);
        redisService.addWord("야채", "야채", 1);
        redisService.addWord("채소", "채소", 1);
        redisService.addWord("과일", "과일", 1);
        redisService.addWord("고기", "고기", 1);
        redisService.addWord("물", "물", 1);
        redisService.addWord("나무", "나무", 1);
        redisService.addWord("한글", "한글", 1);
        redisService.addWord("한문", "한문", 1);
        redisService.addWord("한자", "한자", 1);
        redisService.addWord("수료", "수료", 1);
        redisService.addWord("통과", "통과", 1);
        redisService.addWord("합격", "합껵", 1);
        redisService.addWord("취직", "취직", 1);






        redisService.addSentence("면접보세요", "면접보세요",1);
        redisService.addSentence("안녕하세요", "안녕하세요",1);
        redisService.addSentence("그녀는 정치에 대해서는 새까맣다", "그녀는 정치에 대해서는 새까마타",1);
        redisService.addSentence("빼앗긴 들에도 봄은 오는가", "빼앋낀 들에도 봄은 오는가",1);
        redisService.addSentence("한여름의 무더위", "한여름의 무더위",1);
        redisService.addSentence("늦가을의 해는 벌써 떨어지고 주위는 어둡기 시작했다", "늗까을의 해는 벌써 떨어지고 주위는 어둡기 시작했다",1);
        redisService.addSentence("겨울이 가고 봄이 왔다", "겨울이 가고 봄이 왔다",1);
        redisService.addSentence("티 없이 맑은 목소리", "티 업씨 말근 목쏘리",1);
        redisService.addSentence("그의 부동산은 모두 압류되었다", "그의 부동산은 모두 압류되었다",1);
        redisService.addSentence("기자를 사칭한 사기꾼", "기자를 사칭한 사기꾼",1);
        redisService.addSentence("독립 기념관에서는 광복절을 맞아 기념행사를 준비하였다", "동닙 기념관에서는 광복쩔을 맞아 기념행사를 준비하였다",1);
        redisService.addSentence("오징어 두 마리", "오징어 두 마리",1);
        redisService.addSentence("상처에 약을 바르고 붕대를 감았다", "상처에 약을 바르고 붕대를 감았다",1);
        redisService.addSentence("보름달이 비치다.", "보름달이 비치다",1);
        redisService.addSentence("선물을 보내다", "선물을 보내다",1);
        redisService.addSentence("결혼식에 참석하다", "결혼식에 참서카다",1);
        redisService.addSentence("나도 모르게 한숨이 새어 나왔다", "나도 모르게 한숨이 새어 나왔다",1);
        redisService.addSentence("회전목마를 타고 빙빙 돌아가다", "훼전몽마를 타고 빙빙 돌아가다",1);
        redisService.addSentence("우리나라의 서울은 서울이다", "우리나라의 서울은 서울이다",1);
        redisService.addSentence("한국 사람의 구미에 맞는 음식", "한국 사람의 구미에 맞는 음식",1);
        redisService.addSentence("나는 대학을 졸업하고 진로를 바꿔 무역업에 뛰어들었다", "나는 대학을 조러파고 질로를 바꿔 무여겁에 뛰어들었다",1);
        redisService.addSentence("우리들은 졸업을 앞두고 사진을 찍기로 했다", "우리들은 조럽을 압뚜고 사진을 찍기로 했다",1);

    }
}
