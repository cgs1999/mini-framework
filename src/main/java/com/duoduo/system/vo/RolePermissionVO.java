package com.duoduo.system.vo;

import com.duoduo.system.model.RolePermission;

/**
 * 角色权限关系VO
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午12:53:26
 * @version 1.0.0
 */
public class RolePermissionVO {

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

	/**
	 * 转换Entity为VO
	 * @param entity
	 * @return
	 */
	public static RolePermissionVO fromEntity(RolePermission entity) {
		RolePermissionVO vo = new RolePermissionVO();
		vo.setRoleId(entity.getRoleId());
		vo.setPermissionId(entity.getPermissionId());
		return vo;
	}

	/**
	 * 转换VO为Entity
	 * @param vo
	 * @return
	 */
	public static RolePermission toEntity(RolePermissionVO vo) {
		RolePermission entity = new RolePermission();
		entity.setRoleId(vo.getRoleId());
		entity.setPermissionId(vo.getPermissionId());
		return entity;
	}

}
