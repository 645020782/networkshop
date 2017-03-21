package com.utstar.networkshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.ImageDao;
import com.utstar.networkshop.domain.Image;
import com.utstar.networkshop.service.ImageService;
@Service
@Transactional
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageDao imageDao;
	public List<Image> getImageByProductId(Image img) {
		List<Image> imageList = imageDao.getImageByProductId(img);
		return imageList;
	}
	public void addImg(Image img) {
		imageDao.addImg(img);
	}

}
