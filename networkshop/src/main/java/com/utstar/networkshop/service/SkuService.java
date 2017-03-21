package com.utstar.networkshop.service;

import java.util.List;

import com.utstar.networkshop.domain.Sku;
import com.utstar.networkshop.domain.Type;

public interface SkuService {

	void addSku(Sku sku);

	List<Sku> getSkuList(Sku sku);

	void updateSkuByKey(Sku sku);

	List<Sku> getStock(Integer id);

	Sku getSkuByKey(Integer skuId);

}
