package com.ssafy.api.controller;

import com.ssafy.api.model.*;
import com.ssafy.api.model.CountModel;
import com.ssafy.api.model.PositionModel;
import com.ssafy.api.request.*;
import com.ssafy.api.response.*;
import com.ssafy.api.service.ResidenceService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ResidenceCommercialCount;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 방 검색 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "방 생성, 삭제 API", tags = {"Residence"})
@RestController
@RequestMapping("/api/v1/residences")
public class ResidenceController {

    @Autowired
    ResidenceService residenceService;

    @GetMapping()
    @ApiOperation(value = "매물 구/군/동으로 조회", notes = "매물을 상세 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<ResidenceSearchRes> getResidences(
            @ModelAttribute ResidenceGetReq residenceGetReq, @ApiIgnore Authentication authentication) {
        try {
            ResidenceSearchPaging rooms = residenceService.getResidencesBySiGuDong(residenceGetReq, authentication);
            return ResponseEntity.status(200).body(ResidenceSearchRes.of(rooms));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(ResidenceSearchRes.of(500, "fail"));
        }
    }

    @PostMapping("/ids")
    @ApiOperation(value = "매물 id로 조회", notes = "매물을 id로 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<ResidenceSearchRes> getResidences(
            @RequestBody @ApiParam(value = "매물 아이디로 조회", required = true)ResidenceIdsPostReq residenceIdsPostReq,
            @ApiIgnore Authentication authentication) {
        try {
            ResidenceSearchPaging rooms = residenceService.getResidencesById(residenceIdsPostReq, authentication);
            return ResponseEntity.status(200).body(ResidenceSearchRes.of(rooms));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(ResidenceSearchRes.of(500, "fail"));
        }
    }

    @PostMapping("/estateIds")
    @ApiOperation(value = "매물 부동산 id로 조회", notes = "매물을 부동산 id로 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<ResidenceRes> getResidencesByEstateId(
            @RequestBody @ApiParam(value = "매물 부동산 아이디로 조회", required = true) ResidenceEstateIdsPostReq residenceEstateIdsPostReq
            ) {
        try {
            ResidencePaging residencePaging = residenceService.getResidencesByEstateId(residenceEstateIdsPostReq);
            return ResponseEntity.status(200).body(ResidenceRes.of(residencePaging));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(ResidenceRes.of(500, "fail"));
        }
    }

    @GetMapping("/positions")
    @ApiOperation(value = "전체 동 좌표 조회", notes = "전체 동 좌표를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<PositionRes> getPositions( @RequestParam String dongName ) {
        try {
            List<PositionModel> positionModels = residenceService.getPosition(dongName);
            return ResponseEntity.status(200).body(PositionRes.of(positionModels));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(PositionRes.of(500, "fail"));
        }
    }

    @PostMapping("/detail")
    @ApiOperation(value = "매물 상세필터로 조회", notes = "매물을 상세 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<ResidenceSearchRes> getResidencesDetail(
            @RequestBody @ApiParam(value = "매물 상세", required = true) ResidenceDetailGetReq residenceDetailGetReq, @ApiIgnore Authentication authentication
    ) {
        try {
            ResidenceSearchPaging rooms = residenceService.getResidenceDetails(residenceDetailGetReq, authentication);
            return ResponseEntity.status(200).body(ResidenceSearchRes.of(rooms));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(ResidenceSearchRes.of(500, "fail"));
        }
    }

    @GetMapping("/gucount")
    @ApiOperation(value = "구 매물 개수 조회", notes = "구 매물 개수를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<CountRes> getGuCount() {
        try {
            List<CountModel> guCounts = residenceService.getGetGuCount();
            return ResponseEntity.status(200).body(CountRes.of(guCounts));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(CountRes.of(500, "fail"));
        }
    }

    @GetMapping("/dongcount")
    @ApiOperation(value = "동 매물 개수 조회", notes = "구 매물 개수를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<CountRes> getDongCount() {
        try {
            List<CountModel> dongCounts = residenceService.getGetDongCount();
            return ResponseEntity.status(200).body(CountRes.of(dongCounts));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(CountRes.of(500, "fail"));
        }
    }

    @PostMapping()
    @ApiOperation(value = "매물 생성", notes = "매물을 생성 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> createResidence( ResidencePostReq residence ) {
        try {
            residenceService.createResidence(residence);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (NoSuchElementException | IOException e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }

    @PatchMapping()
    @ApiOperation(value = "매물 수정", notes = "매울정보를 수정 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> patchResidence(
            @RequestBody ResidencePatchReq residence, @RequestParam long residenceId) {
        try {
            residenceService.patchResidence(residence, residenceId);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (NoSuchElementException | IOException e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }

    @DeleteMapping()
    @ApiOperation(value = "매물 삭제", notes = "매물정보를 수정 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteResidence(
            @RequestParam @ApiParam(value = "매물 삭제", required = true) Long residenceId
    ) {
        try {
            residenceService.deleteResidence(residenceId);
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
        }
    }

    @GetMapping("/commercialcount")
    @ApiOperation(value = "동별 상권 개수", notes = "동별 상권 개수")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<CommercialCountRes> getCommercialCountByDongName( @RequestParam String dongName ) {
        try {
            List<CommercialCountModel> commercialCountModel = residenceService.getCommercialCount(dongName);
            return ResponseEntity.status(200).body(CommercialCountRes.of(commercialCountModel));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(CommercialCountRes.of(500, "fail"));
        }
    }

    @PostMapping("/recommend")
    @ApiOperation(value = "매물 추천", notes = "매물을 가중치가 높은 순으로 추천한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<ResidenceRecommendRes> getRecommend(
            @RequestBody @ApiParam(value = "매물 추천", required = true) ResidenceRecommendPostReq residenceRecommendPostReq,
            @ApiIgnore Authentication authentication) {
        try {
            List<RecommendModel> recommendModel = residenceService.getRecommendResidence(residenceRecommendPostReq, authentication);
            return ResponseEntity.status(200).body(ResidenceRecommendRes.of(recommendModel));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(ResidenceRecommendRes.of(500, "fail"));
        }
    }

    @PostMapping("/residencecommercialcount")
    @ApiOperation(value = "매물 주변 상권 개수", notes = "매물 주변 상권 개수를 출력한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<ResidenceCommercialRes> getResidenceCommercial(
            @RequestParam long residenceId) {
        try {
            List<ResidenceCommercialCountModel> residenceCommercialCount = residenceService.getResidenceCommercial(residenceId);
            return ResponseEntity.status(200).body(ResidenceCommercialRes.of(residenceCommercialCount));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(ResidenceCommercialRes.of(500, "fail"));
        }
    }

    @PostMapping("/commercialposition")
    @ApiOperation(value = "매물 주변 상권 위치", notes = "매물 주변 상권 위치를 출력한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<ResidenceCommercialPositionRes> getResidenceCommercialPosition(
            @RequestParam long residenceId) {
        try {
            List<ResidenceCommercialPositionModel> residenceCommercialPositionModel = residenceService.getResidenceCommercialPosition(residenceId);
            return ResponseEntity.status(200).body(ResidenceCommercialPositionRes.of(residenceCommercialPositionModel));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(500).body(ResidenceCommercialPositionRes.of(500, "fail"));
        }
    }

//    @DeleteMapping()
//    @ApiOperation(value = "방 종류 삭제", notes = "방 종류를 삭제한다.")
//    @ApiResponses({
//            @ApiResponse(code = 201, message = "삭제 성공"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public ResponseEntity<BaseResponseBody> deleteResidenceType(
//            @RequestBody @ApiParam(value = "매물 삭제", required = true) Long residenceId) {
//        try {
//            residenceService.deleteResidence(residenceId);
//            return ResponseEntity.status(200).body(UserLoginPostRes.of(201, "Success"));
//        }catch (Exception e){
//            return ResponseEntity.status(500).body(UserLoginPostRes.of(500, "fail"));
//        }
//    }
}
