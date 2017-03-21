package com.utstar.networkshop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.utstar.networkshop.domain.Image;

public interface ImageDao {

	List<Image> getImageByProductId(Image image);

	void addImg(Image img);

}
