package com.utstar.networkshop.dao;


import java.util.List;

import com.utstar.networkshop.domain.Sku;

public interface SkuDao {

	void addSku(Sku sku);

	List<Sku> getSkuList(Sku sku);

	void updateSkuByKey(Sku sku);

	List<Sku> getStock(Integer productId);

	Sku getSkuByKey(Integer skuId);

}
