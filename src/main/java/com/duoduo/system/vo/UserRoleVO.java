package com.duoduo.system.vo;

import com.duoduo.system.model.UserRole;

/**
 * 用户角色关系VO
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:07:33
 * @version 1.0.0
 */
public class UserRoleVO {

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

	/**
	 * 转换Entity为VO
	 * @param entity
	 * @return
	 */
	public static UserRoleVO fromEntity(UserRole entity) {
		UserRoleVO vo = new UserRoleVO();
		vo.setUserId(entity.getUserId());
		vo.setRoleId(entity.getRoleId());
		return vo;
	}

	/**
	 * 转换VO为Entity
	 * @param vo
	 * @return
	 */
	public static UserRole toEntity(UserRoleVO vo) {
		UserRole entity = new UserRole();
		entity.setUserId(vo.getUserId());
		entity.setRoleId(vo.getRoleId());
		return entity;
	}
}
