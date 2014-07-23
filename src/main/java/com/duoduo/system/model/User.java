package com.duoduo.system.model;

import com.duoduo.core.model.IdEntity;

/**
 * 用户实体
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午12:34:56
 * @version 1.0.0
 */
public class User extends IdEntity {

	private static final long serialVersionUID = 4419879348130150713L;

	/** 帐号 */
	private String account;
	/** 姓名 */
	private String name;
	/** 密码 */
	private String password;
	/** 加密私钥 */
	private String salt;
	/** 电子邮箱 */
	private String email;
	/** 联系电话 */
	private String phone;
	/** 状态 */
	private Integer status;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
