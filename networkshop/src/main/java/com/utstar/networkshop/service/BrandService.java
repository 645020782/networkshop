package com.utstar.networkshop.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.utstar.networkshop.domain.Brand;
import com.utstar.networkshop.domain.Pagination;

public interface BrandService {
	public Pagination getBrandListWithPage(Brand brand);

	public void delBrandById(Brand brand);

	public void editBrandById(Brand brand);

	public void addBrand(Brand brand);

	public Brand getBrandById(Integer brandId);

	public List<Brand> getAllBrand(Brand brand);
}
