package com.utstar.networkshop.dao;

import java.util.List;

import com.utstar.networkshop.domain.TestVo;

public interface TestDao {
	public List<TestVo> findtestbyid(String id);
}
