package com.utstar.networkshop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.DetailDao;
import com.utstar.networkshop.domain.Detail;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.service.DetailService;

/**
 * 订单子项(订单详情)
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class DetailServiceImpl implements DetailService {

	@Resource
	DetailDao detailDao;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addDetail(Detail detail) {
		return detailDao.addDetail(detail);
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Detail getDetailByKey(Integer id) {
		return detailDao.getDetailByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Detail> getDetailsByKeys(List<Integer> idList) {
		return detailDao.getDetailsByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return detailDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return detailDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateDetailByKey(Detail detail) {
		return detailDao.updateDetailByKey(detail);
	}
	
	@Transactional(readOnly = true)
	public Pagination getDetailListWithPage(Detail detail) {
		Pagination p = new Pagination();
		p.setCurrentPage(detail.getCurrentPage());
		p.setPageSize(detail.getPageSize());
		p.setRecordTotal(detailDao.getDetailListCount(detail));
		p.setRows(detailDao.getDetailListWithPage(detail));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Detail> getDetailList(Detail detail) {
		return detailDao.getDetailList(detail);
	}
}