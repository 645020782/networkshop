package com.utstar.networkshop.dao;

import java.util.List;




import org.apache.ibatis.annotations.Param;

import com.utstar.networkshop.domain.Brand;
public interface BrandDao {
	public List<Brand> getBrandListWithPage(Brand brand);

	public Integer getBrandRecodeTotal(Brand brand);

	public void delBrandById(Brand brand);

	public void editBrandById(Brand brand);

	public void addBrand(Brand brand);

	public Brand getBrandById(@Param(value="brandId")Integer brandId);

	public List<Brand> getAllBrand(Brand brand);
}
