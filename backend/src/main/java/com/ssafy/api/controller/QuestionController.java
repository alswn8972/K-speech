package com.ssafy.api.controller;

import com.ssafy.api.model.QnAModel;
import com.ssafy.api.request.QuestionOptionPostReq;
import com.ssafy.api.request.QuestionPatchReq;
import com.ssafy.api.request.QuestionResultPostReq;
import com.ssafy.api.response.QuestionOptionRes;
import com.ssafy.api.response.QuestionRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.QuestionOptionService;
import com.ssafy.api.service.QuestionResultService;
import com.ssafy.api.service.QuestionService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Question;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 질문 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "질문 API", tags = {"Question"})
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionOptionService questionOptionService;

    @Autowired
    QuestionResultService questionResultService;

    @PostMapping()
    @ApiOperation(value = "질문 생성", notes = "사용자에게 제공할 질문을 생성 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> createQuestion(
            @RequestBody @ApiParam(value = "질문 내용", required = true) String questionContent) {
        try {
            questionService.createQuestion(questionContent);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }

    @GetMapping()
    @ApiOperation(value = "질문 목록 조회", notes = "전체 질문 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<QuestionRes> getQuestions() {
        try {
            List<Question> questions = questionService.getQuestion();
            return ResponseEntity.status(201).body(QuestionRes.of(questions));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(QuestionRes.of(500, "fail"));
        }
    }

    @PatchMapping()
    @ApiOperation(value = "질문 목록 수정", notes = "회원 본인의 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> patchQuestion(
            @RequestBody @ApiParam(value = "질문 목록 수정", required = true) QuestionPatchReq questionInfo) {
        try {
            questionService.patchQuestion(questionInfo);
            return ResponseEntity.status(201).body(UserLoginPostRes.of(201, "Success"));
        }catch (Exception e){
            return ResponseEntity.status(500).body(UserLoginPostRes.of(500, "fail"));
        }
    }

    @DeleteMapping()
    @ApiOperation(value = "질문 삭제", notes = "질문을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "삭제 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> deleteQuestion(
            @RequestBody @ApiParam(value = "삭제 질문 리스트", required = true) List<Long> questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.status(200).body(UserLoginPostRes.of(201, "Success"));
        }catch (Exception e){
            return ResponseEntity.status(500).body(UserLoginPostRes.of(500, "Fail"));
        }
    }

    @PostMapping("/options")
    @ApiOperation(value = "질문 옵션 생성", notes = "사용자에게 제공할 질문을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> createQuestionOption(
            @RequestBody @ApiParam(value = "질문 옵션", required = true) QuestionOptionPostReq questionOptionInfo) {
        try {
            questionOptionService.createQuestionOption(questionOptionInfo);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }

    @GetMapping("/options")
    @ApiOperation(value = "질문 옵션 조회", notes = "사용자에게 제공할 질문을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<QuestionOptionRes> getQuestionOption() {
        try {
            List<QnAModel> questionOptions = questionOptionService.getQuestionOption();
            return ResponseEntity.status(201).body(QuestionOptionRes.of(questionOptions));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(QuestionOptionRes.of(500, "fail"));
        }
    }

    @DeleteMapping("/options")
    @ApiOperation(value = "질문 옵션 삭제", notes = "사용자에게 제공할 질문을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteQuestionOption(
            @RequestBody @ApiParam(value = "질문 옵션 리스트 삭제", required = true) List<Long> questionOptionId) {
        try {
            questionOptionService.deleteQuestionOption(questionOptionId);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }

    @PostMapping("/results")
    @ApiOperation(value = "질문 답변 생성", notes = "사용자 답변을 추가한다..")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> addQuestionResult(
            @ApiIgnore Authentication authentication, @RequestBody @ApiParam(value = "답변 저장", required = true) QuestionResultPostReq questionResultPostReq) {
        try {
            questionResultService.createQuestionResult(authentication, questionResultPostReq);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }
}