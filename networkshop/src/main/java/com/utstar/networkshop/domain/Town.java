package com.utstar.networkshop.domain;

import java.io.Serializable;

/**
 * 县/区
 * @author lixu
 * @Date [2014-3-28 下午04:38:53]
 */
public class Town extends Pagination<Town> implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;
	private String name;
	private String citycode;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	@Override
	public String toString() {
		return "Town [id=" + id + ", code=" + code + ", name=" + name
				+ ", citycode=" + citycode + "]";
	}
	
}
