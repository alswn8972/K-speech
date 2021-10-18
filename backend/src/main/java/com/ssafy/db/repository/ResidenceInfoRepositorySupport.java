package com.ssafy.db.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.model.ResidencePaging;
import com.ssafy.api.model.PositionModel;
import com.ssafy.api.request.*;
import com.ssafy.db.entity.QResidenceInfo;
import com.ssafy.db.entity.QUserFavorite;
import com.ssafy.db.entity.ResidenceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 방 검색 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ResidenceInfoRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QResidenceInfo qresidenceInfo = QResidenceInfo.residenceInfo;
    QUserFavorite quserFavorite = QUserFavorite.userFavorite;
    int pageSize = 10;

    public ResidencePaging findRooms(ResidenceDetailGetReq residenceDetailGetReq) {
        JPAQuery<ResidenceInfo> residences = jpaQueryFactory.select(qresidenceInfo).from(qresidenceInfo);

        BooleanBuilder builder = new BooleanBuilder();
        for (int i = 0; i< residenceDetailGetReq.getResidenceType().size(); i++)
            builder.or(qresidenceInfo.residenceType.type.eq(residenceDetailGetReq.getResidenceType().get(i)));
        residences.where(builder);

        builder = new BooleanBuilder();
        for (int i = 0; i< residenceDetailGetReq.getRoomStrucDeail().size(); i++)
            builder.or(qresidenceInfo.structure.eq(residenceDetailGetReq.getRoomStrucDeail().get(i)));
        residences.where(builder);

        builder = new BooleanBuilder();
        for (int i = 0; i< residenceDetailGetReq.getFloorDetail().size(); i++)
            builder.or(qresidenceInfo.myFloor.eq(residenceDetailGetReq.getFloorDetail().get(i)));
        residences.where(builder);

        builder = new BooleanBuilder();
        for (int i = 0; i< residenceDetailGetReq.getResidenceCategory().size(); i++)
            builder.or(qresidenceInfo.residenceCategory.categoryName.eq(residenceDetailGetReq.getResidenceCategory().get(i)));
        residences.where(builder);

        builder = new BooleanBuilder();
        if (residenceDetailGetReq.getStartPrice() > 0) builder.and(qresidenceInfo.cost.goe(residenceDetailGetReq.getStartPrice()));
        if (residenceDetailGetReq.getEndPrice() > 0) builder.and(qresidenceInfo.cost.loe(residenceDetailGetReq.getEndPrice()));
        if (residenceDetailGetReq.getStartJPrice() > 0) builder.and(qresidenceInfo.jeonseCost.goe(residenceDetailGetReq.getStartJPrice()));
        if (residenceDetailGetReq.getEndJPrice() > 0) builder.and(qresidenceInfo.jeonseCost.loe(residenceDetailGetReq.getEndJPrice()));
        if (residenceDetailGetReq.getStartWPrice() > 0) builder.and(qresidenceInfo.wolseCost.goe(residenceDetailGetReq.getStartWPrice()));
        if (residenceDetailGetReq.getEndWPrice() > 0) builder.and(qresidenceInfo.wolseCost.loe(residenceDetailGetReq.getEndWPrice()));
        if (residenceDetailGetReq.getStartManagePrice() > 0) builder.and(qresidenceInfo.manageCost.goe(residenceDetailGetReq.getStartManagePrice()));
        if (residenceDetailGetReq.getEndManagePrice() > 0) builder.and(qresidenceInfo.manageCost.loe(residenceDetailGetReq.getEndManagePrice()));
        if (residenceDetailGetReq.getStartArea() > 0) builder.and(qresidenceInfo.area.goe(residenceDetailGetReq.getStartArea()));
        if (residenceDetailGetReq.getEndArea() > 0) builder.and(qresidenceInfo.area.loe(residenceDetailGetReq.getEndArea()));
        if (residenceDetailGetReq.getStartDeposit() > 0) builder.and(qresidenceInfo.deposit.goe(residenceDetailGetReq.getStartDeposit()));
        if (residenceDetailGetReq.getEndDeposit() > 0) builder.and(qresidenceInfo.deposit.loe(residenceDetailGetReq.getEndDeposit()));
        if (residenceDetailGetReq.getGugun() != null) builder.and(qresidenceInfo.dong.Gugun.gugunName.eq(residenceDetailGetReq.getGugun()));
        if (residenceDetailGetReq.getDong() != null) builder.and(qresidenceInfo.dong.dongName.eq(residenceDetailGetReq.getDong()));
        residences.where(builder);

        // 정렬
        if(residenceDetailGetReq.getSortType()!=null){
            if(residenceDetailGetReq.getSortType().equals("cost")){
                if(residenceDetailGetReq.getSortOrder().equals("asc")) residences.orderBy(qresidenceInfo.wolseCost.asc(), qresidenceInfo.jeonseCost.asc(), qresidenceInfo.cost.asc());
                if(residenceDetailGetReq.getSortOrder().equals("desc")) residences.orderBy(qresidenceInfo.wolseCost.desc(), qresidenceInfo.jeonseCost.desc(), qresidenceInfo.cost.desc());
            }else if(residenceDetailGetReq.getSortType().equals("area")){
                if(residenceDetailGetReq.getSortOrder().equals("asc")) residences.orderBy(qresidenceInfo.area.asc());
                if(residenceDetailGetReq.getSortOrder().equals("desc")) residences.orderBy(qresidenceInfo.area.desc());
            }else if(residenceDetailGetReq.getSortType().equals("favorite")){
                if(residenceDetailGetReq.getSortOrder().equals("asc")) residences.orderBy(qresidenceInfo.favoriteCnt.asc());
                if(residenceDetailGetReq.getSortOrder().equals("desc")) residences.orderBy(qresidenceInfo.favoriteCnt.desc());
            }
        }

        ResidencePaging residencePaging = new ResidencePaging();
        residencePaging.setResidenceInfos(residences.offset((residenceDetailGetReq.getPageNum()-1)*pageSize).limit(pageSize).fetch());
        residencePaging.setPageSize((residences.fetchCount()-1)/10+1);

        return residencePaging;
    }

    public ResidencePaging findRoomsBySiGuDong(ResidenceGetReq residenceDetailGetReq) {
        JPAQuery<ResidenceInfo> residences = jpaQueryFactory.select(qresidenceInfo).from(qresidenceInfo);
        BooleanBuilder builder = new BooleanBuilder();
        if (residenceDetailGetReq.getGugun() != null) builder.and(qresidenceInfo.dong.Gugun.gugunName.eq(residenceDetailGetReq.getGugun()));
        if (residenceDetailGetReq.getDong() != null) builder.and(qresidenceInfo.dong.dongName.eq(residenceDetailGetReq.getDong()));
        residences.where(builder);

        // 정렬
        if(residenceDetailGetReq.getSortType()!=null){
            if(residenceDetailGetReq.getSortType().equals("cost")){
                if(residenceDetailGetReq.getSortOrder().equals("asc")) residences.orderBy(qresidenceInfo.wolseCost.asc(), qresidenceInfo.jeonseCost.asc(), qresidenceInfo.cost.asc());
                if(residenceDetailGetReq.getSortOrder().equals("desc")) residences.orderBy(qresidenceInfo.wolseCost.desc(), qresidenceInfo.jeonseCost.desc(), qresidenceInfo.cost.desc());
            } else if(residenceDetailGetReq.getSortType().equals("area")){
                if(residenceDetailGetReq.getSortOrder().equals("asc")) residences.orderBy(qresidenceInfo.area.asc());
                if(residenceDetailGetReq.getSortOrder().equals("desc")) residences.orderBy(qresidenceInfo.area.desc());
            } else if(residenceDetailGetReq.getSortType().equals("favorite")){
                if(residenceDetailGetReq.getSortOrder().equals("asc")) residences.orderBy(qresidenceInfo.favoriteCnt.asc());
                if(residenceDetailGetReq.getSortOrder().equals("desc")) residences.orderBy(qresidenceInfo.favoriteCnt.desc());
            }
        }

        ResidencePaging residencePaging = new ResidencePaging();
        residencePaging.setResidenceInfos(residences.offset((residenceDetailGetReq.getPageNum()-1)*pageSize).limit(pageSize).fetch());
        residencePaging.setPageSize((residences.fetchCount()-1)/10+1);
        return residencePaging;
    }

    public long findGuGunCount(Long id) {
        long count = jpaQueryFactory.select(qresidenceInfo).from(qresidenceInfo)
                .where(qresidenceInfo.dong.Gugun.id.eq(id)).fetchCount();
        return count;
    }

    public long findDongCount(Long id) {
        long count = jpaQueryFactory.select(qresidenceInfo).from(qresidenceInfo)
                .where(qresidenceInfo.dong.id.eq(id)).fetchCount();
        return count;
    }

    public List<PositionModel> findPositionsByDongName(String dongName) {
        List<PositionModel> positionModels = new ArrayList<>();
        List<Tuple> list= jpaQueryFactory.select(qresidenceInfo.id, qresidenceInfo.lon, qresidenceInfo.lat).from(qresidenceInfo)
                .where(qresidenceInfo.dong.dongName.eq(dongName)).fetch();

        for(Tuple i: list){
            PositionModel positionModel = new PositionModel();
            positionModel.setId(i.get(qresidenceInfo.id));
            positionModel.setLat(i.get(qresidenceInfo.lat));
            positionModel.setLon(i.get(qresidenceInfo.lon));
            positionModels.add(positionModel);
        }

        return positionModels;
    }

    public ResidencePaging findRoomsByEstateId(ResidenceEstateIdsPostReq residenceEstateIdsPostReq) {
        JPAQuery<ResidenceInfo> residenceInfos = jpaQueryFactory.select(qresidenceInfo).from(qresidenceInfo)
                .where(qresidenceInfo.estateInfo.id.eq(residenceEstateIdsPostReq.getEstateId()));

        ResidencePaging residencePaging = new ResidencePaging();
        residencePaging.setResidenceInfos(residenceInfos.offset((residenceEstateIdsPostReq.getPageNum()-1)*pageSize).limit(pageSize).fetch());
        residencePaging.setPageSize((residenceInfos.fetchCount()-1)/10+1);
        return residencePaging;
    }

    public List<ResidenceInfo> findRecommendResidence(ResidenceRecommendPostReq residenceRecommendPostReq) {
        JPAQuery<ResidenceInfo> residenceInfos = jpaQueryFactory.select(qresidenceInfo).from(qresidenceInfo);

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qresidenceInfo.residenceType.id.eq(residenceRecommendPostReq.getResiType()));
        residenceInfos.where(builder);

        builder = new BooleanBuilder();
        for (int i = 0; i< residenceRecommendPostReq.getResiCategory().size(); i++)
            builder.or(qresidenceInfo.residenceCategory.id.eq(residenceRecommendPostReq.getResiCategory().get(i)));
        residenceInfos.where(builder);

        if (residenceRecommendPostReq.getResiType() == 1) {
            if(residenceRecommendPostReq.getResiCostStart() > 0) builder.and(qresidenceInfo.jeonseCost.goe(residenceRecommendPostReq.getResiCostStart()));
            if(residenceRecommendPostReq.getResiCostEnd() > 0) builder.and(qresidenceInfo.jeonseCost.loe(residenceRecommendPostReq.getResiCostEnd()));
        } else if(residenceRecommendPostReq.getResiType() == 2){
            if(residenceRecommendPostReq.getResiCostStart() > 0) builder.and(qresidenceInfo.cost.goe(residenceRecommendPostReq.getResiCostStart()));
            if(residenceRecommendPostReq.getResiCostEnd() > 0) builder.and(qresidenceInfo.cost.loe(residenceRecommendPostReq.getResiCostEnd()));
        }else if(residenceRecommendPostReq.getResiType() == 3){
            if(residenceRecommendPostReq.getResiDepositStart() > 0) builder.and(qresidenceInfo.deposit.goe(residenceRecommendPostReq.getResiDepositStart()));
            if(residenceRecommendPostReq.getResiDepositEnd() > 0) builder.and(qresidenceInfo.deposit.loe(residenceRecommendPostReq.getResiDepositEnd()));
            if(residenceRecommendPostReq.getResiCostStart() > 0) builder.and(qresidenceInfo.wolseCost.goe(residenceRecommendPostReq.getResiCostStart()));
            if(residenceRecommendPostReq.getResiCostEnd() > 0) builder.and(qresidenceInfo.wolseCost.loe(residenceRecommendPostReq.getResiCostEnd()));
        }
        residenceInfos.where(builder);

        builder = new BooleanBuilder();
        for(int i=0; i<residenceRecommendPostReq.getDong().size(); i++)
            builder.or(qresidenceInfo.dong.id.eq(residenceRecommendPostReq.getDong().get(i)));
        residenceInfos.where(builder);

        return residenceInfos.fetch();
    }

    public ResidencePaging findById(ResidenceIdsPostReq residenceIdsPostReq) {
        JPAQuery<ResidenceInfo> residenceInfos = jpaQueryFactory.select(qresidenceInfo).from(qresidenceInfo)
                .where(qresidenceInfo.id.in(residenceIdsPostReq.getResidenceIds()));

        // 정렬
        if(residenceIdsPostReq.getSortType()!=null){
            if(residenceIdsPostReq.getSortType().equals("cost")){
                if(residenceIdsPostReq.getSortOrder().equals("asc")) residenceInfos.orderBy(qresidenceInfo.wolseCost.asc(), qresidenceInfo.jeonseCost.asc(), qresidenceInfo.cost.asc());
                if(residenceIdsPostReq.getSortOrder().equals("desc")) residenceInfos.orderBy(qresidenceInfo.wolseCost.desc(), qresidenceInfo.jeonseCost.desc(), qresidenceInfo.cost.desc());
            } else if(residenceIdsPostReq.getSortType().equals("area")){
                if(residenceIdsPostReq.getSortOrder().equals("asc")) residenceInfos.orderBy(qresidenceInfo.area.asc());
                if(residenceIdsPostReq.getSortOrder().equals("desc")) residenceInfos.orderBy(qresidenceInfo.area.desc());
            } else if(residenceIdsPostReq.getSortType().equals("favorite")){
                if(residenceIdsPostReq.getSortOrder().equals("asc")) residenceInfos.orderBy(qresidenceInfo.favoriteCnt.asc());
                if(residenceIdsPostReq.getSortOrder().equals("desc")) residenceInfos.orderBy(qresidenceInfo.favoriteCnt.desc());
            }
        }

        ResidencePaging residencePaging = new ResidencePaging();
        residencePaging.setResidenceInfos(residenceInfos.offset((residenceIdsPostReq.getPageNum()-1)*pageSize).limit(pageSize).fetch());
        residencePaging.setPageSize((residenceInfos.fetchCount()-1)/10+1);

        return residencePaging;
    }
}
