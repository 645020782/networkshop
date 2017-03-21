package com.utstar.networkshop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.BrandDao;
import com.utstar.networkshop.dao.TestDao;
import com.utstar.networkshop.domain.Brand;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.domain.TestVo;
import com.utstar.networkshop.service.BrandService;
@Service
@Transactional
public class BrandServiceImpl implements BrandService{
	@Resource
	private BrandDao brandDao;
	public Pagination<Brand> getBrandListWithPage(Brand brand) {
		Pagination<Brand> pagination = new Pagination<Brand>();
		if(brand.getCurrentPage()==null||brand.getCurrentPage()<1){
			pagination.setCurrentPage(1);
		}else{
			pagination.setCurrentPage(brand.getCurrentPage());
		}
		Integer beginRecord = (pagination.getCurrentPage()-1)*pagination.getPageSize();
		pagination.setBeginRecord(beginRecord);
		pagination.setBeginPage(brand.getBeginPage());
		brand.setBeginRecord(beginRecord);
		List<Brand> brandList = brandDao.getBrandListWithPage(brand);
		Integer recordTotal = brandDao.getBrandRecodeTotal(brand);
		Integer endPage = (recordTotal+brand.getPageSize()-1)/brand.getPageSize();
		pagination.setBeginPage(brand.getBeginPage());
		pagination.setRecordTotal(recordTotal);
		pagination.setRows(brandList);
		pagination.setEndPage(endPage);
		return pagination;
	}
	public void delBrandById(Brand brand) {
		brandDao.delBrandById(brand);
	}
	public void editBrandById(Brand brand) {
		brandDao.editBrandById(brand);
	}
	public void addBrand(Brand brand) {
		brandDao.addBrand(brand);
	}
	public Brand getBrandById(Integer brandId) {
		Brand brand = new Brand();
		brand = brandDao.getBrandById(brandId);
		return brand;
	}
	public List<Brand> getAllBrand(Brand brand) {
		List<Brand> l = brandDao.getAllBrand(brand);
		return l;
	}

}
