package com.ssafy.api.service;

import com.ssafy.api.model.DongModel;
import com.ssafy.api.request.ResidenceDetailGetReq;
import com.ssafy.api.request.ResidenceGetReq;
import com.ssafy.common.auth.UserDetail;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *	방 검색 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("residenceSearchService")
public class SearchServiceImpl implements SearchService {
	@Autowired
	ResidenceCategoryRepository residenceCategoryRepository;

	@Autowired
	ResidenceTypeRepository residenceTypeRepository;

	@Autowired
	SearchService searchService;

	@Autowired
	UserService userService;

	@Autowired
	ResidenceCategoryRepositorySupport residenceCategoryRepositorySupport;

	@Autowired
	ResidenceTypeRepositorySupport residenceTypeRepositorySupport;

	@Autowired
	SiRepository siRepository;

	@Autowired
	GuGunRepositorySupport guGunRepositorySupport;

	@Autowired
	DongRepositorySupport dongRepositorySupport;

	@Override
	public void createResidenceCategory(String type) {
		ResidenceCategory residenceCategory = new ResidenceCategory();
		residenceCategory.setCategoryName(type);
		residenceCategoryRepository.save(residenceCategory);
	}

	@Override
	public List<ResidenceCategory> getResidenceCategory() {
		return residenceCategoryRepository.findAll();
	}

	@Override
	public void deleteResidenceCategory(List<Long> residenceCategory) {
		for (Long id:residenceCategory) residenceCategoryRepository.deleteById(id);
	}

	@Override
	public void createResidenceType(String type) {
		ResidenceType residenceType = new ResidenceType();
		residenceType.setType(type);
		residenceTypeRepository.save(residenceType);
	}

	@Override
	public List<ResidenceType> getResidenceType() {
		return residenceTypeRepository.findAll();
	}

	@Override
	public void deleteResidenceType(List<Long> residenceType) {
		for (Long id:residenceType) residenceTypeRepository.deleteById(id);
	}

	@Override
	public List<Si> getSi() {
		return siRepository.findAll();
	}

	@Override
	public List<String> getGuGun(String siName) {
		List<Gugun> guGuns = guGunRepositorySupport.getGuGunBySiName(siName);
		List<String> guGunList = new ArrayList<>();
		for(int i=0; i<guGuns.size(); i++)
			guGunList.add(guGuns.get(i).getGugunName());
		return guGunList;
	}

	@Override
	public List<DongModel> getDong(String guGunName) {
		List<Dong> dongs = dongRepositorySupport.getDongByGuGunName(guGunName);
		List<DongModel> dongModels = new ArrayList<>();

		for(int i=0; i<dongs.size(); i++){
			DongModel dongModel = new DongModel();
			dongModel.setId(dongs.get(i).getId());
			dongModel.setDongName(dongs.get(i).getDongName());
			dongModel.setLat(dongs.get(i).getLat());
			dongModel.setLon(dongs.get(i).getLon());
			dongModels.add(dongModel);
		}

		return dongModels;
	}
}