package com.utstar.networkshop.service;

import java.util.List;

import com.utstar.networkshop.domain.Feature;

public interface FeatureService {
	public List<Feature> getAllFeature(Feature feature);

	public List<Feature> getFeatureList(Feature feature);

}
