package com.utstar.networkshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.ImageDao;
import com.utstar.networkshop.dao.TypeDao;
import com.utstar.networkshop.domain.Image;
import com.utstar.networkshop.domain.Type;
import com.utstar.networkshop.service.ImageService;
import com.utstar.networkshop.service.TypeService;
@Service
@Transactional
public class TypeServiceImpl implements TypeService{
	@Autowired
	private TypeDao typeDao;

	public List<Type> getAllType(Type type) {
		List<Type> list = typeDao.getAllType(type);
		return list;
	}

	public List<Type> getTypeList(Type type) {
		List<Type> l = typeDao.getTypeList(type);
		return l;
	}

}
