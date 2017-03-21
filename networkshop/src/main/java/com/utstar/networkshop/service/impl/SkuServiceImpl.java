package com.utstar.networkshop.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.SkuDao;
import com.utstar.networkshop.domain.Product;
import com.utstar.networkshop.domain.Sku;
import com.utstar.networkshop.service.ColorService;
import com.utstar.networkshop.service.ProductService;
import com.utstar.networkshop.service.SkuService;
@Service
@Transactional
public class SkuServiceImpl implements SkuService{
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorService colorService;
	@Resource
	ProductService productService;
	public void addSku(Sku sku) {
		skuDao.addSku(sku);
	}

	public List<Sku> getSkuList(Sku sku) {
		List<Sku> l = skuDao.getSkuList(sku);
		//颜色加载完结
		for(Sku s : l){
			s.setColor(colorService.getColorByKey(s.getColorId()));
		}
		return l;
	}

	public void updateSkuByKey(Sku sku) {
		skuDao.updateSkuByKey(sku);
	}

	public List<Sku> getStock(Integer productId) {
		List<Sku> l = skuDao.getStock(productId);
		//颜色加载完结
		for(Sku s : l){
			s.setColor(colorService.getColorByKey(s.getColorId()));
		}
		return l;
	}

	public Sku getSkuByKey(Integer skuId) {
		Sku sku = skuDao.getSkuByKey(skuId);
		//通过商品ID
		Product product = productService.getProductById(sku.getProductId());
		
		sku.setProduct(product);
		//颜色加载
		sku.setColor(colorService.getColorByKey(sku.getColorId()));
		
		return sku;
	}

}
