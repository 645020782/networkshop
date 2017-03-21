package com.utstar.networkshop.dao;

import java.util.List;

import com.utstar.networkshop.domain.Town;


public interface TownDao {

	/**
	 * 添加
	 * @param town
	 */
	public Integer addTown(Town town);

	/**
	 * 根据主键查找
	 * @param townQuery
	 */
	public Town getTownByKey(Integer id);

	/**
	 * 根据主键批量查找
	 * @param townQuery
	 */
	public List<Town> getTownsByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * @param townQuery
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * @param townQuery
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * @param townQuery
	 */
	public Integer updateTownByKey(Town town);

	/**
	 * 分页查询
	 * @param townQuery
	 */
	public List<Town> getTownListWithPage(Town town);

	/**
	 * 集合查询
	 * @param townQuery
	 */
	public List<Town> getTownList(Town town);
	
	/**
	 * 总条数
	 * @param townQuery
	 */
	public int getTownListCount(Town town);
}
