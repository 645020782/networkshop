package com.utstar.networkshop.dao;

import java.util.List;

import com.utstar.networkshop.domain.City;


public interface CityDao {

	/**
	 * 添加
	 * @param city
	 */
	public Integer addCity(City city);

	/**
	 * 根据主键查找
	 * @param cityQuery
	 */
	public City getCityByKey(Integer id);

	/**
	 * 根据主键批量查找
	 * @param cityQuery
	 */
	public List<City> getCitysByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * @param cityQuery
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * @param cityQuery
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * @param cityQuery
	 */
	public Integer updateCityByKey(City city);

	/**
	 * 分页查询
	 * @param cityQuery
	 */
	public List<City> getCityListWithPage(City city);

	/**
	 * 集合查询
	 * @param cityQuery
	 */
	public List<City> getCityList(City city);
	
	/**
	 * 总条数
	 * @param cityQuery
	 */
	public int getCityListCount(City city);
}
