package com.utstar.networkshop.domain;

import com.utstar.common.web.contants.Constants;

public class Brand extends Pagination<Brand>{
	private Integer brandId;
	private String brandName;
	private String description;
	private Integer sort;
	private String imgUrl;
	private String isDisplay;
	private String webSize;
	
	//获取全路径
		public String getAllUrl(){
			return Constants.IMAGE_URL + imgUrl;
		}
		
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getWebSize() {
		return webSize;
	}
	public void setWebSize(String webSize) {
		this.webSize = webSize;
	}
	@Override
	public String toString() {
		return "Brand [brandId=" + brandId + ", brandName=" + brandName
				+ ", description=" + description + ", sort=" + sort
				+ ", imgUrl=" + imgUrl + ", isDisplay=" + isDisplay
				+ ", webSize=" + webSize + "]";
	}
	
}
