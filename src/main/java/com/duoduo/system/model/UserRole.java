package com.duoduo.system.model;

/**
 * 用户角色对应关系
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午12:52:39
 * @version 1.0.0
 */
public class UserRole {

	/** 用户ID */
	private Long userId;
	/** 角色ID */
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
