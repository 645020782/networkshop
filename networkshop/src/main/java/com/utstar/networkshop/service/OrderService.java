package com.utstar.networkshop.service;

import java.util.List;

import com.utstar.networkshop.domain.BuyCart;
import com.utstar.networkshop.domain.Order;
import com.utstar.networkshop.domain.Pagination;


/**
 * 
 * @author lixu
 * @Date [2014-3-28 下午01:50:28]
 */
public interface OrderService {
	/**
	 * 基本插入
	 * 
	 * @return
	 */
	public Integer addOrder(Order order,BuyCart buyCart);

	/**
	 * 根据主键查询
	 */
	public Order getOrderByKey(Integer id);

	/**
	 * 根据主键批量查询
	 */
	public List<Order> getOrdersByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * 
	 * @return
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateOrderByKey(Order order);

	/**
	 * 根据条件查询分页查询
	 * 
	 * @param orderQuery
	 *            查询条件
	 * @return
	 */
	public Pagination getOrderListWithPage(Order order);

	/**
	 * 根据条件查询
	 * 
	 * @param orderQuery
	 *            查询条件
	 * @return
	 */
	public List<Order> getOrderList(Order order);
}
