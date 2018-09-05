package com.comtom.bc.server.repository.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * <b>用户基本信息表[aos_sys_user]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_user_ext")
public class SysUserExt {

	@Id
	@Column(name = "id_")
	private String id;


	@Column(name = "email_")
	private String email;


	@Column(name = "fixed_phone_")
	private String fixedPhone;

	@Column(name = "mobile_phone_")
	private String mobilePhone;


	@Column(name = "address_")
	private String address;


	@Column(name = "zip_")
	private String zip;


	@Column(name = "birthday_")
	private String birthday;


	@Column(name = "idno_")
	private String idno;



	@Column(name = "qq_")
	private String qq;


	@Column(name = "dynamic_field_")
	private String dynamicField;


	@Column(name = "remark_")
	private String remark;


	@Column(name = "filed1_")
	private String filed1;


	@Column(name = "filed2_")
	private String filed2;


	@Column(name = "filed3_")
	private String filed3;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFixedPhone() {
		return fixedPhone;
	}

	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}


	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(String dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFiled1() {
		return filed1;
	}

	public void setFiled1(String filed1) {
		this.filed1 = filed1;
	}

	public String getFiled2() {
		return filed2;
	}

	public void setFiled2(String filed2) {
		this.filed2 = filed2;
	}

	public String getFiled3() {
		return filed3;
	}

	public void setFiled3(String filed3) {
		this.filed3 = filed3;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
}