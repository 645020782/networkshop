package com.utstar.networkshop.dao;

import java.util.List;

import com.utstar.networkshop.domain.Color;

public interface ColorDao {

	List<Color> getAllColor(Color color);

	Color getColorByKey(Integer colorId);

}
