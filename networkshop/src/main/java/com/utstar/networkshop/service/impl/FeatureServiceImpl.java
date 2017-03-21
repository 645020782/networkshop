package com.utstar.networkshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.FeatureDao;
import com.utstar.networkshop.dao.ImageDao;
import com.utstar.networkshop.dao.TypeDao;
import com.utstar.networkshop.domain.Feature;
import com.utstar.networkshop.domain.Image;
import com.utstar.networkshop.domain.Type;
import com.utstar.networkshop.service.FeatureService;
import com.utstar.networkshop.service.ImageService;
import com.utstar.networkshop.service.TypeService;
@Service
@Transactional
public class FeatureServiceImpl implements FeatureService{
	@Autowired
	private FeatureDao featureDao;

	public List<Feature> getAllFeature(Feature feature) {
		List<Feature> list = featureDao.getAllFeature(feature);
		return list;
	}

	public List<Feature> getFeatureList(Feature feature) {
		List<Feature> l = featureDao.getFeatureList(feature);
		return l;
	}


}
