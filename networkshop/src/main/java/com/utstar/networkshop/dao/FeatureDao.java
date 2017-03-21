package com.utstar.networkshop.dao;

import java.util.List;

import com.utstar.networkshop.domain.Feature;

public interface FeatureDao {

	List<Feature> getAllFeature(Feature feature);

	List<Feature> getFeatureList(Feature feature);

}
