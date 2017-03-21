package com.utstar.networkshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.ColorDao;
import com.utstar.networkshop.dao.ImageDao;
import com.utstar.networkshop.dao.TypeDao;
import com.utstar.networkshop.domain.Color;
import com.utstar.networkshop.domain.Image;
import com.utstar.networkshop.domain.Type;
import com.utstar.networkshop.service.ColorService;
import com.utstar.networkshop.service.ImageService;
import com.utstar.networkshop.service.TypeService;
@Service
@Transactional
public class ColorServiceImpl implements ColorService{
	@Autowired
	private ColorDao colorDao;

	public List<Color> getAllColor(Color color) {
		List<Color> list = colorDao.getAllColor(color);
		return list;
	}

	public Color getColorByKey(Integer colorId) {
		Color color = colorDao.getColorByKey(colorId);
		return color;
	}

}
