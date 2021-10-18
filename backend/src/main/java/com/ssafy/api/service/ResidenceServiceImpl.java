package com.ssafy.api.service;

import com.ssafy.api.model.*;
import com.ssafy.api.model.CountModel;
import com.ssafy.api.model.PositionModel;
import com.ssafy.api.request.*;
import com.ssafy.common.auth.UserDetail;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 *	매물 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("residenceService")
public class ResidenceServiceImpl implements ResidenceService {
	@Autowired
	ResidenceInfoRepositorySupport residenceInfoRepositorySupport;

	@Autowired
	ResidenceInfoRepository residenceInfoRepository;

	@Autowired
	ResidenceTypeRepository residenceTypeRepository;

	@Autowired
	ResidenceCategoryRepository residenceCategoryRepository;

	@Autowired
	EstateInfoRepository estateInfoRepository;

	@Autowired
	DongRepositorySupport dongRepositorySupport;

	@Autowired
	DongRepository dongRepository;

	@Autowired
	GuGunRepository guGunRepository;

	@Autowired
	FeatureRepositorySupport featureRepositorySupport;

	@Autowired
	FeatureRepository featureRepository;

	@Autowired
	UserFavoriteRepositorySupport userFavoriteRepositorySupport;

	@Autowired
	CommercialCountRepositorySupport commercialCountRepositorySupport;

	@Autowired
	ResidenceWeightRepositorySupport residenceWeightRepositorySupport;

	@Autowired
	ResidenceCommercialCountRepositorySupport residenceCommercialCountRepositorySupport;

	@Autowired
	ResidenceCommercialPositionRepositorySupport residenceCommercialPositionRepositorySupport;


	@Override
	public ResidenceSearchPaging getResidenceDetails(ResidenceDetailGetReq residenceDetailGetReq, Authentication authentication) {
		ResidenceSearchPaging residenceSearchPaging = new ResidenceSearchPaging();
		ResidencePaging residencePaging = residenceInfoRepositorySupport.findRooms(residenceDetailGetReq);
		List<ResidenceInfo> residenceInfos = residencePaging.getResidenceInfos();
		long pageSize = residencePaging.getPageSize();

		List<ResidenceModel> residenceModels = new ArrayList<>();
		UserDetail userDetail = null;
		if (authentication != null)
			userDetail = (UserDetail) authentication.getDetails();
		for(int i=0; i<residenceInfos.size(); i++){
			ResidenceModel residenceModel = new ResidenceModel();
			residenceModel.setResidenceInfo(residenceInfos.get(i));
			if (authentication != null)
				residenceModel.setPresent(userFavoriteRepositorySupport.checkIsFavorite(userDetail.getUser().getId(), residenceInfos.get(i).getId()));
			residenceModels.add(residenceModel);
		}

		residenceSearchPaging.setResidenceModels(residenceModels);
		residenceSearchPaging.setPageSize(pageSize);
		return residenceSearchPaging;
	}

	@Override
	public ResidenceSearchPaging getResidencesById(ResidenceIdsPostReq residenceIdsPostReq, Authentication authentication) {
		ResidenceSearchPaging residenceSearchPaging = new ResidenceSearchPaging();

		List<ResidenceModel> residenceModels = new ArrayList<>();
		ResidencePaging residencePaging = residenceInfoRepositorySupport.findById(residenceIdsPostReq);
		UserDetail userDetail=null;
		if (authentication != null)
			userDetail = (UserDetail) authentication.getDetails();


		for(ResidenceInfo residenceInfo : residencePaging.getResidenceInfos()){
			ResidenceModel residenceModel = new ResidenceModel();
			residenceModel.setResidenceInfo(residenceInfo);
			if (authentication != null)
				residenceModel.setPresent(userFavoriteRepositorySupport.checkIsFavorite(userDetail.getUser().getId(), residenceInfo.getId()));
			residenceModels.add(residenceModel);
		}

		residenceSearchPaging.setResidenceModels(residenceModels);
		residenceSearchPaging.setPageSize((residenceIdsPostReq.getResidenceIds().size()-1)/10+1);

		return residenceSearchPaging;
	}

	@Override
	public ResidencePaging getResidencesByEstateId(ResidenceEstateIdsPostReq residenceEstateIdsPostReq) {
		ResidencePaging residencePaging = residenceInfoRepositorySupport.findRoomsByEstateId(residenceEstateIdsPostReq);
		return residencePaging;
	}

	@Override
	public void patchResidence(ResidencePatchReq residence, long residenceId){
		ResidenceInfo residenceInfo = residenceInfoRepository.findById(residenceId).get();
		residenceInfoRepository.save(setPatchResidence(residenceInfo,residence));
	}

	private ResidenceInfo setPatchResidence(ResidenceInfo residenceInfo, ResidencePatchReq residence) {
		residenceInfo.setCost(residence.getCost());
		residenceInfo.setJeonseCost(residence.getJeonseCost());
		residenceInfo.setWolseCost(residence.getWolseCost());
		residenceInfo.setManageCost(residence.getManageCost());
		residenceInfo.setName(residence.getName());

		if(residence.getFeature() != null)
			residenceInfo.setFeature(setFeature(residence.getFeature()));
		return residenceInfo;
	}

	@Override
	public List<CommercialCountModel> getCommercialCount(String dongName) {
		List<CommercialCountModel> commercialCountModels = new ArrayList<>();
		List<CommercialCount> commercialCounts = commercialCountRepositorySupport.findCommercialCountByDongName(dongName);

		for (CommercialCount commercialCount : commercialCounts) {
			CommercialCountModel commercialCountModel = new CommercialCountModel();
			commercialCountModel.setCount(commercialCount.getCount());
			commercialCountModel.setCommercialName(commercialCount.getCommercialCategory().getCategoryName());
			commercialCountModels.add(commercialCountModel);
		}
		return commercialCountModels;
	}

	@Override
	public List<RecommendModel> getRecommendResidence(ResidenceRecommendPostReq residenceRecommendPostReq, Authentication authentication) {
		List<RecommendModel> recommendModels = new ArrayList<>();
		List<RecommendModel> recommendModelReturn = new ArrayList<>();
		List<ResidenceInfo> residenceInfos = residenceInfoRepositorySupport.findRecommendResidence(residenceRecommendPostReq);
		UserDetail userDetail = null;
		if (authentication != null)
			userDetail = (UserDetail) authentication.getDetails();

		System.out.println(residenceInfos.size());
		for(ResidenceInfo residenceInfo : residenceInfos){
			RecommendModel recommendModel = new RecommendModel();
			recommendModel.setResidenceInfo(residenceInfo);
			recommendModel.setTotalWeight(calTotalWeight(residenceInfo.getId(), residenceRecommendPostReq.getScore()));
			if (authentication != null)
				recommendModel.setPresent(userFavoriteRepositorySupport.checkIsFavorite(userDetail.getUser().getId(), residenceInfo.getId()));
			recommendModels.add(recommendModel);
		}

		Collections.sort(recommendModels, new Comparator<RecommendModel>() {
			@Override
			public int compare(RecommendModel o1, RecommendModel o2) {
				if (o2.getTotalWeight()-o1.getTotalWeight()>0)
					return 1;
				if (o2.getTotalWeight()-o1.getTotalWeight()==0)
					return 0;
				return -1;
			}
		});

		for(int i=0; i<recommendModels.size(); i++){
			if(i==10) break;
			recommendModelReturn.add(recommendModels.get(i));
		}

		return recommendModelReturn;
	}

	@Override
	public List<ResidenceCommercialCountModel> getResidenceCommercial(long residenceId) {
		List<ResidenceCommercialCount> residenceCommercialCounts = residenceCommercialCountRepositorySupport.findResidenceCommercialCountByResidenceId(residenceId);
		List<ResidenceCommercialCountModel> residenceCommercialCountModels = new ArrayList<>();

		for(ResidenceCommercialCount residenceCommercialCount : residenceCommercialCounts){
			ResidenceCommercialCountModel residenceCommercialCountModel= new ResidenceCommercialCountModel();
			residenceCommercialCountModel.setResidenceId(residenceCommercialCount.getResidenceInfo().getId());
			residenceCommercialCountModel.setCafeCount(residenceCommercialCount.getCafeCount());
			residenceCommercialCountModel.setConvenienceCount(residenceCommercialCount.getConvenienceCount());
			residenceCommercialCountModel.setHealthCount(residenceCommercialCount.getHealthCount());
			residenceCommercialCountModel.setBusCount(residenceCommercialCount.getBusCount());
			residenceCommercialCountModel.setBicycleCount(residenceCommercialCount.getBicycleCount());
			residenceCommercialCountModel.setSubwayCount(residenceCommercialCount.getSubwayCount());
			residenceCommercialCountModels.add(residenceCommercialCountModel);
		}

		return residenceCommercialCountModels;
	}

	@Override
	public List<ResidenceCommercialPositionModel> getResidenceCommercialPosition(long residenceId) {
		List<ResidenceCommercialPositionModel> residenceCommercialPositionModels = new ArrayList<>();
		List<ResidenceCommercialPosition> residenceCommercialPositions = residenceCommercialPositionRepositorySupport.findCommercialPositionByResidenceId(residenceId);
		for (ResidenceCommercialPosition residenceCommercialPosition:residenceCommercialPositions) {
			if(residenceCommercialPosition.getCommercialInfo().getCommercialCategory().getCategoryName().equals("따릉이")){
				ResidenceCommercialPositionModel residenceCommercialPositionModel = new ResidenceCommercialPositionModel();
				residenceCommercialPositionModel.setLat(residenceCommercialPosition.getCommercialInfo().getLat());
				residenceCommercialPositionModel.setLon(residenceCommercialPosition.getCommercialInfo().getLon());
				residenceCommercialPositionModels.add(residenceCommercialPositionModel);
			}
		}
		return residenceCommercialPositionModels;
	}

	private double calTotalWeight(Long residenceId, List<Double> score) {
		List<ResidenceWeight> residenceWeights = residenceWeightRepositorySupport.findWeightsByResidenceId(residenceId);

		double totalWeight = 0;
		for (int i=0; i<residenceWeights.size(); i++)
			totalWeight += (Double.parseDouble(residenceWeights.get(i).getWeight()) * score.get(residenceWeights.get(i).getCommercialCategory().getId().intValue()-1));

		return totalWeight;
	}

	private ResidenceInfo setResidence(ResidenceInfo residenceInfo, ResidencePostReq residence) throws IOException {
		if(residence.getDong() != null)
			residenceInfo.setDong(dongRepositorySupport.findDongByName(residence.getDong()));
		if(residence.getThumbnails()!=null) {
			List<ImageUrl> imageUrls = new ArrayList<>();
			for (MultipartFile thumbnail : residence.getThumbnails()) {
				ImageUrl imageUrl = new ImageUrl();
				imageUrl.setUrl(saveThumbnail(thumbnail));
				imageUrls.add(imageUrl);
			}
			residenceInfo.setImageUrl(imageUrls);
		}
		if(residence.getFeature() != null)
			residenceInfo.setFeature(setFeature(residence.getFeature()));
		residenceInfo.setEstateInfo(estateInfoRepository.findById(residence.getEstateId()).get());
		if(residence.getResidenceType() != null)
			residenceInfo.setResidenceType(residenceTypeRepository.findById(residence.getResidenceType()).get());
		if(residence.getResidenceCategory() != null)
		residenceInfo.setResidenceCategory(residenceCategoryRepository.findById(residence.getResidenceCategory()).get());
		residenceInfo.setArea(residence.getArea());
		residenceInfo.setBuildingFloor(residence.getBuildingFloor());
		residenceInfo.setCost(residence.getCost());
		residenceInfo.setJeonseCost(residence.getJeonseCost());
		residenceInfo.setWolseCost(residence.getWolseCost());
		residenceInfo.setManageCost(residence.getManageCost());
		residenceInfo.setDirection(residence.getDirection());
		residenceInfo.setLat(residence.getLat());
		residenceInfo.setLon(residence.getLon());
		residenceInfo.setMyFloor(residence.getMyFloor());
		residenceInfo.setName(residence.getName());
		residenceInfo.setStructure(residence.getStructure());
		residenceInfo.setFavoriteCnt(0);
		residenceInfo.setSale(false);
		return residenceInfo;
	}

	@Override
	public  ResidenceSearchPaging getResidencesBySiGuDong(ResidenceGetReq residenceGetReq, Authentication authentication) {
		ResidenceSearchPaging residenceSearchPaging = new ResidenceSearchPaging();
		ResidencePaging residencePaging = residenceInfoRepositorySupport.findRoomsBySiGuDong(residenceGetReq);
		List<ResidenceModel> residenceModels = new ArrayList<>();
		List<ResidenceInfo> residenceInfos = residencePaging.getResidenceInfos();

		UserDetail userDetail = null;
		if (authentication != null)
			userDetail = (UserDetail) authentication.getDetails();

		for(int i=0; i<residenceInfos.size(); i++){
			ResidenceModel residenceModel = new ResidenceModel();
			residenceModel.setResidenceInfo(residenceInfoRepository.findById(residenceInfos.get(i).getId()).get());
			if (authentication != null)
				residenceModel.setPresent(userFavoriteRepositorySupport.checkIsFavorite(userDetail.getUser().getId(), residenceInfos.get(i).getId()));
			residenceModels.add(residenceModel);
		}

		residenceSearchPaging.setResidenceModels(residenceModels);
		residenceSearchPaging.setPageSize(residencePaging.getPageSize());
		return residenceSearchPaging;
	}

	@Override
	public void deleteResidence(Long residenceId) {
		ResidenceInfo residenceInfo = residenceInfoRepository.findById(residenceId).get();

//		for (ImageUrl imageUrl : residenceInfo.getImageUrl()){
//			System.out.println(imageUrl.getUrl());
//			residenceInfo.setImageUrl(null);
//		}
//
//		for (Feature feature : residenceInfo.getFeature())
//			residenceInfo.setFeature(null);
//
//
//		residenceInfo.setDong(null);
//		residenceInfo.setEstateInfo(null);
//		residenceInfo.setResidenceCategory(null);
//		residenceInfo.setResidenceType(null);
		residenceInfo.setSale(true);
		residenceInfoRepository.save(residenceInfo);
//		residenceInfoRepository.deleteById(residenceId);

	}

	@Override
	public void createResidence(ResidencePostReq residence) throws IOException {
		ResidenceInfo residenceInfo = new ResidenceInfo();
		residenceInfoRepository.save(setResidence(residenceInfo,residence));
	}

	@Override
	public List<CountModel> getGetGuCount() {
		List<CountModel> countModelList = new ArrayList<>();
		List<Gugun> gugunList = guGunRepository.findAll();

		for (Gugun gugun : gugunList) {
			CountModel countModel = new CountModel();
			countModel.setLon(gugun.getLon());
			countModel.setLat(gugun.getLat());
			countModel.setName(gugun.getGugunName());
			countModel.setCount(residenceInfoRepositorySupport.findGuGunCount(gugun.getId()));
			countModelList.add(countModel);
		}
		return countModelList;
	}

	@Override
	public List<CountModel> getGetDongCount() {
		List<CountModel> countModelList = new ArrayList<>();
		List<Dong> dongList = dongRepository.findAll();

		for (Dong dong : dongList) {
			CountModel countModel = new CountModel();
			countModel.setLon(dong.getLon());
			countModel.setLat(dong.getLat());
			countModel.setName(dong.getDongName());
			countModel.setCount(residenceInfoRepositorySupport.findDongCount(dong.getId()));
			countModelList.add(countModel);
		}
		return countModelList;
	}

	@Override
	public List<PositionModel> getPosition(String dongName) {
		List<PositionModel> positionModels = residenceInfoRepositorySupport.findPositionsByDongName(dongName);
		return positionModels;
	}

	private List<Feature> setFeature(List<String> featureList) {
		List<Feature> features = new ArrayList<>();
		for(int i=0; i<featureList.size(); i++){
			Optional<Feature> featureOptional = featureRepositorySupport.findFeatureByFeatureName(featureList.get(i));
			Feature feature = new Feature();
			if(!featureOptional.isPresent()){
				feature.setFeatureName(featureList.get(i));
				featureRepository.save(feature);
				feature = featureRepositorySupport.findFeatureByFeatureName(featureList.get(i)).get();
			}else feature = featureOptional.get();
			features.add(feature);
		}
		return features;
	}

	private String saveThumbnail(MultipartFile thumbnail) throws IOException {
		String path = "images/";
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();

		String url = "";
		if (thumbnail != null) {
			String originalFileExtension = thumbnail.getOriginalFilename().substring(thumbnail.getOriginalFilename().lastIndexOf("."));
			String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;

			url = "images" + File.separator + new_file_name;
			Path pathAbs = Paths.get(url).toAbsolutePath();
			thumbnail.transferTo(pathAbs.toFile());
		}
		return url;
	}
}

