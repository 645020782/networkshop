package com.utstar.networkshop.service;

import java.util.List;

import com.utstar.networkshop.domain.Color;
import com.utstar.networkshop.domain.Type;

public interface ColorService {
	public List<Color> getAllColor(Color color);

	public Color getColorByKey(Integer colorId);

}
