package com.utstar.networkshop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 购买者
 * @author lixu
 * @Date [2014-3-28 下午04:38:53]
 */
public class Buyer extends Pagination<Buyer> implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;
	private String password;
	private Gender gender;
	private String email;
	private String realName;
	private Date registerTime;
	private Integer provinceId;
	private Integer cityId;
	private Integer townId;
	private String addr;
	private Integer isDel;
	
	public enum Gender {

		MAN{
			public String getName(){return "男";}
		},
		WOMAN{
			public String getName(){return "女";}
		},
		SECRECY{
			public String getName(){return "保密";}
		};
		
		public abstract String getName();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getTownId() {
		return townId;
	}
	public void setTownId(Integer townId) {
		this.townId = townId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Buyer [username=" + username + ", password=" + password
				+ ", gender=" + gender + ", email=" + email + ", realName="
				+ realName + ", registerTime=" + registerTime + ", provinceId="
				+ provinceId + ", cityId=" + cityId + ", townId=" + townId
				+ ", addr=" + addr + ", isDel=" + isDel + "]";
	}
	
}
