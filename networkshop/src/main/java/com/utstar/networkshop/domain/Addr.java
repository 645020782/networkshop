package com.utstar.networkshop.domain;

import java.io.Serializable;

/**
 * 地址
 * @author lixu
 * @Date [2014-3-28 下午04:38:53]
 */
public class Addr extends Pagination<Addr> implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer buyerId;
	private String name;
	private String city;
	private String addrs;
	private String phone;
	private Integer isDef;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getIsDef() {
		return isDef;
	}
	public void setIsDef(Integer isDef) {
		this.isDef = isDef;
	}
	public String getAddrs() {
		return addrs;
	}
	public void setAddrs(String addrs) {
		this.addrs = addrs;
	}
	@Override
	public String toString() {
		return "Addr [id=" + id + ", buyerId=" + buyerId + ", name=" + name
				+ ", city=" + city + ", addrs=" + addrs + ", phone=" + phone
				+ ", isDef=" + isDef + "]";
	}
	
}
