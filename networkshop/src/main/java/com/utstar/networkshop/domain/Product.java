package com.utstar.networkshop.domain;

import java.util.Date;

public class Product extends Pagination<Product>{
	private	Integer	productId;
	private	String	no;
	private	String	name;
	private	String	weight;
	private	String	isNew;
	private	String	isHot;
	private	String	isCommend;
	private	Date	createTime;
	private	String	createUserId;
	private	Date	checkTime;
	private	String	checkUserId;
	private	String	isShow;
	private	String	isDel;
	private	String	typeId;
	private	String	keyWords;
	private	String	sales;
	private	String	description;
	private	String	packageList;
	private	String	feature;
	private	String	color;
	private	String	size;
	private Integer brandId;
	private Image img;
	
	
	public Product() {
		super();
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	public String getIsCommend() {
		return isCommend;
	}
	public void setIsCommend(String isCommend) {
		this.isCommend = isCommend;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPackageList() {
		return packageList;
	}
	public void setPackageList(String packageList) {
		this.packageList = packageList;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", no=" + no + ", name="
				+ name + ", weight=" + weight + ", isNew=" + isNew + ", isHot="
				+ isHot + ", isCommend=" + isCommend + ", createTime="
				+ createTime + ", createUserId=" + createUserId
				+ ", checkTime=" + checkTime + ", checkUserId=" + checkUserId
				+ ", isShow=" + isShow + ", isDel=" + isDel + ", typeId="
				+ typeId + ", keyWords=" + keyWords + ", sales=" + sales
				+ ", description=" + description + ", packageList="
				+ packageList + ", feature=" + feature + ", color=" + color
				+ ", size=" + size + "]";
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	
}
