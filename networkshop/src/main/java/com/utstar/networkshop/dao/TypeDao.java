package com.utstar.networkshop.dao;

import java.util.List;

import com.utstar.networkshop.domain.Type;

public interface TypeDao {

	List<Type> getAllType(Type type);

	List<Type> getTypeList(Type type);

}
