package com.duoduo.system.vo;

import com.duoduo.core.util.DateUtils;
import com.duoduo.core.vo.BaseVO;
import com.duoduo.system.model.User;

/**
 * 用户VO
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:27:24
 * @version 1.0.0
 */
public class UserVO extends BaseVO {

	/** 帐号 */
	private String account;
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

	// 角色信息
	private String roleIds;
	private String roleNames;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	/**
	 * 转换Entity为VO
	 * @param entity
	 * @return
	 */
	public static UserVO fromEntity(User entity) {
		UserVO vo = new UserVO();
		vo.setId(entity.getId());
		vo.setAccount(entity.getAccount());
		vo.setName(entity.getName());
		vo.setPassword(entity.getPassword());
		vo.setSalt(entity.getSalt());
		vo.setEmail(entity.getEmail());
		vo.setPhone(entity.getPhone());
		vo.setStatus(entity.getStatus());
		vo.setCreateTime(DateUtils.toDatetimeString(entity.getCreateTime()));
		vo.setUpdateTime(DateUtils.toDatetimeString(entity.getUpdateTime()));
		return vo;
	}

	/**
	 * 转换VO为Entity
	 * @param vo
	 * @return
	 */
	public static User toEntity(UserVO vo) {
		User entity = new User();
		entity.setId(vo.getId());
		entity.setAccount(vo.getAccount());
		entity.setName(vo.getName());
		entity.setPassword(vo.getPassword());
		entity.setSalt(vo.getSalt());
		entity.setEmail(vo.getEmail());
		entity.setPhone(vo.getPhone());
		entity.setStatus(vo.getStatus());
		return entity;
	}
}
