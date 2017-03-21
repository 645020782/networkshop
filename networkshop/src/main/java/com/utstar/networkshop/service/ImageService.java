package com.utstar.networkshop.service;

import java.util.List;

import com.utstar.networkshop.domain.Image;

public interface ImageService {

	public List<Image> getImageByProductId(Image image);

	public void addImg(Image img);

}
