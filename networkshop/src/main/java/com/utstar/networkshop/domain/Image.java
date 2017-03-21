package com.utstar.networkshop.domain;

import com.utstar.common.web.contants.Constants;

public class Image {
	private Integer imgId;
	private Integer productId;
	private String imgUrl;
	private String isDef;
	
	public String getAllUrl(){
		return Constants.IMAGE_URL+imgUrl;
	}
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getIsDef() {
		return isDef;
	}
	public void setIsDef(String isDef) {
		this.isDef = isDef;
	}
	
}
