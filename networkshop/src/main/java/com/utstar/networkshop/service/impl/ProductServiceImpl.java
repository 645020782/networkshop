package com.utstar.networkshop.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utstar.networkshop.dao.ProductDao;
import com.utstar.networkshop.domain.Image;
import com.utstar.networkshop.domain.Product;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.domain.Sku;
import com.utstar.networkshop.service.ImageService;
import com.utstar.networkshop.service.ProductService;
import com.utstar.networkshop.service.SkuService;
@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	@Resource
	private ProductDao productDao;
	@Autowired
	private ImageService imageService;
	@Autowired
	private SkuService skuService;
	public Pagination<Product> getProductListWithPage(Product product) {
		Pagination<Product> pagination = new Pagination<Product>();
		if(product.getCurrentPage()==null||product.getCurrentPage()<1){
			pagination.setCurrentPage(1);
		}else{
			pagination.setCurrentPage(product.getCurrentPage());
		}
		Integer beginRecord = (pagination.getCurrentPage()-1)*pagination.getPageSize();
		pagination.setBeginRecord(beginRecord);
		pagination.setBeginPage(product.getBeginPage());
		product.setBeginRecord(beginRecord);
		List<Product> productList = productDao.getProductListWithPage(product);
		for(Product p:productList){
			Image i = new Image();
			i.setProductId(p.getProductId());
			i.setIsDef("1");
			List<Image> l = imageService.getImageByProductId(i);
			if(l.size()>0){
				i = l.get(0);
			}
			p.setImg(i);
		}
		Integer recordTotal = productDao.getProductRecodeTotal(product);
		Integer endPage = (recordTotal+product.getPageSize()-1)/product.getPageSize();
		pagination.setBeginPage(product.getBeginPage());
		pagination.setRecordTotal(recordTotal);
		pagination.setRows(productList);
		pagination.setEndPage(endPage);
		return pagination;
	}
	public void delProductById(Product product) {
		productDao.delProductById(product);
	}
	public void editProductById(Product product) {
		productDao.editProductById(product);
	}
	public void addProduct(Product product) {
		//商品编号
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String no = df.format(new Date());
				product.setNo(no);
				//添加时间
				product.setCreateTime(new Date());
				//影响到行数   返回商品ID
				//商品保存  
				productDao.addProduct(product);
				//1:商品   图片   sku
				//2:图片
				//1)设置图片商品ID
				product.getImg().setProductId(product.getProductId());
				//2)
				product.getImg().setIsDef("1");;
				imageService.addImg(product.getImg());
				//3:保存Sku    9,13,...
				//  S M  ...
				//实例化一个Sku对象
				Sku sku = new Sku();
				//商品ID
				sku.setProductId(product.getProductId());
				//运费
				sku.setDeliveFee(10.00);
				//售价
				sku.setSkuPrice(0.00);
				//市场价
				sku.setMarketPrice(0.00);
				//库存
				sku.setStockInventory(0);
				//购买限制
				sku.setSkuUpperLimit(0);
				//添加时间
				sku.setCreateTime(new Date());
				//是否最新
				sku.setLastStatus(1);
				//商品
				sku.setSkuType(1);
				//销量
				sku.setSales(0);
				for(String color : product.getColor().split(",")){
					//颜色ID
					sku.setColorId(Integer.parseInt(color));
					
					for(String size : product.getSize().split(",")){
						//尺码
						sku.setSize(size);
						//保存SKu
						skuService.addSku(sku);
					}
					
				}
				//return i;
	}
	public Product getProductById(Integer productId) {
		Product p = productDao.getProductById(productId);
		Image img = new Image();
		img.setProductId(p.getProductId());
		img.setIsDef("1");;
		List<Image> imgs = imageService.getImageByProductId(img);
		p.setImg(imgs.get(0));
		return p;
	}

}
