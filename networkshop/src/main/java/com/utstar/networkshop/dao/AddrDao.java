package com.utstar.networkshop.dao;

import java.util.List;

import com.utstar.networkshop.domain.Addr;


public interface AddrDao {

	/**
	 * 添加
	 * @param addr
	 */
	public Integer addAddr(Addr addr);

	/**
	 * 根据主键查找
	 * @param addrQuery
	 */
	public Addr getAddrByKey(Integer id);

	/**
	 * 根据主键批量查找
	 * @param addrQuery
	 */
	public List<Addr> getAddrsByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * @param addrQuery
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * @param addrQuery
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * @param addrQuery
	 */
	public Integer updateAddrByKey(Addr addr);

	/**
	 * 分页查询
	 * @param addrQuery
	 */
	public List<Addr> getAddrListWithPage(Addr addr);

	/**
	 * 集合查询
	 * @param addrQuery
	 */
	public List<Addr> getAddrList(Addr addr);
	
	/**
	 * 总条数
	 * @param addrQuery
	 */
	public int getAddrListCount(Addr addr);
}
