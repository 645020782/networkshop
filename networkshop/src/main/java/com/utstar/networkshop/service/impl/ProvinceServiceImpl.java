package com.utstar.networkshop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.ProvinceDao;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.domain.Province;
import com.utstar.networkshop.service.ProvinceService;

/**
 * 省
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

	@Resource
	ProvinceDao provinceDao;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addProvince(Province province) {
		return provinceDao.addProvince(province);
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Province getProvinceByKey(Integer id) {
		return provinceDao.getProvinceByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Province> getProvincesByKeys(List<Integer> idList) {
		return provinceDao.getProvincesByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return provinceDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return provinceDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateProvinceByKey(Province province) {
		return provinceDao.updateProvinceByKey(province);
	}
	
	@Transactional(readOnly = true)
	public Pagination getProvinceListWithPage(Province province) {
		Pagination p = new Pagination();
		p.setCurrentPage(province.getCurrentPage());
		p.setPageSize(province.getPageSize());
		p.setRecordTotal(provinceDao.getProvinceListCount(province));
		p.setRows(provinceDao.getProvinceListWithPage(province));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Province> getProvinceList(Province province) {
		return provinceDao.getProvinceList(province);
	}
}
