package com.utstar.networkshop.service;

import com.utstar.networkshop.domain.Brand;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.domain.Product;

public interface ProductService {

	Pagination<Product> getProductListWithPage(Product product);

	void editProductById(Product product);

	void delProductById(Product product);

	void addProduct(Product product);

	Product getProductById(Integer productId);
}
