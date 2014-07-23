package com.duoduo.system.model;

/**
 * 角色权限关系
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午12:53:26
 * @version 1.0.0
 */
public class RolePermission {

	/** 角色ID */
	private Long roleId;
	/** 权限ID */
	private Long permissionId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

}
