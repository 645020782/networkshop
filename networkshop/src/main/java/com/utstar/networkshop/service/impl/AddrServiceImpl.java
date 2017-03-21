package com.utstar.networkshop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.AddrDao;
import com.utstar.networkshop.domain.Addr;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.service.AddrService;
/**
 * 地址
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class AddrServiceImpl implements AddrService {

	@Resource
	AddrDao addrDao;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addAddr(Addr addr) {
		return addrDao.addAddr(addr);
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Addr getAddrByKey(Integer id) {
		return addrDao.getAddrByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Addr> getAddrsByKeys(List<Integer> idList) {
		return addrDao.getAddrsByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return addrDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return addrDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateAddrByKey(Addr addr) {
		return addrDao.updateAddrByKey(addr);
	}
	
	@Transactional(readOnly = true)
	public Pagination getAddrListWithPage(Addr addr) {
		Pagination p = new Pagination();
		p.setCurrentPage(addr.getCurrentPage());
		p.setPageSize(addr.getPageSize());
		p.setRecordTotal(addrDao.getAddrListCount(addr));
		p.setRows(addrDao.getAddrListWithPage(addr));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Addr> getAddrList(Addr addr) {
		return addrDao.getAddrList(addr);
	}
}
