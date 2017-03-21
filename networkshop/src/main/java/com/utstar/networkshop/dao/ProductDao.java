package com.utstar.networkshop.dao;

import java.util.List;






import org.apache.ibatis.annotations.Param;

import com.utstar.networkshop.domain.Brand;
import com.utstar.networkshop.domain.Product;
public interface ProductDao {

	List<Product> getProductListWithPage(Product product);

	Integer getProductRecodeTotal(Product product);

	void delProductById(Product product);

	void editProductById(Product product);

	void addProduct(Product product);

	Product getProductById(@Param(value="productId")Integer productId);
}
