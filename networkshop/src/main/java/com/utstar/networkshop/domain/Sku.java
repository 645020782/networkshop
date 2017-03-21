package com.utstar.networkshop.domain;

import java.util.Date;

public class Sku {
	private	Integer	skuId;
	private	Integer	productId;
	private	Integer	colorId;
	private	Double	deliveFee;
	private	Double	skuPrice;
	private	Integer	stockInventory;
	private	Integer	skuUpperLimit;
	private	String	location;
	private	Integer	skuSort;
	private	String	skuName;
	private	Double	marketPrice;
	private	Date	createTime;
	private	Date	updateTime;
	private	Integer	createUserId;
	private	Integer	updateUserId;
	private	Integer	lastStatus;
	private	Integer	skuType;
	private	Integer	sales;
	private String size;
	private String skuImg;
	private Color color;
	private Product product;
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getColorId() {
		return colorId;
	}
	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}
	public Double getDeliveFee() {
		return deliveFee;
	}
	public void setDeliveFee(Double deliveFee) {
		this.deliveFee = deliveFee;
	}
	public Double getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(Double skuPrice) {
		this.skuPrice = skuPrice;
	}
	public Integer getStockInventory() {
		return stockInventory;
	}
	public void setStockInventory(Integer stockInventory) {
		this.stockInventory = stockInventory;
	}
	public Integer getSkuUpperLimit() {
		return skuUpperLimit;
	}
	public void setSkuUpperLimit(Integer skuUpperLimit) {
		this.skuUpperLimit = skuUpperLimit;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getSkuSort() {
		return skuSort;
	}
	public void setSkuSort(Integer skuSort) {
		this.skuSort = skuSort;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Integer getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(Integer lastStatus) {
		this.lastStatus = lastStatus;
	}
	public Integer getSkuType() {
		return skuType;
	}
	public void setSkuType(Integer skuType) {
		this.skuType = skuType;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSkuImg() {
		return skuImg;
	}
	public void setSkuImg(String skuImg) {
		this.skuImg = skuImg;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
