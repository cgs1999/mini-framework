package com.duoduo.system.model;

import com.duoduo.core.model.IdEntity;

/**
 * 权限实体
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午13:59:29
 * @version 1.0.0
 */
public class Permission extends IdEntity {

	private static final long serialVersionUID = 7855793569562605313L;

	/** 角色名称 */
	private String name;
	/** 所属权限分类Id */
	private Long permissionCategoryId;
	/** 所属权限分类名称 */
	private String permissionCategoryName;
	/** 备注 */
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPermissionCategoryId() {
		return permissionCategoryId;
	}

	public void setPermissionCategoryId(Long permissionCategoryId) {
		this.permissionCategoryId = permissionCategoryId;
	}

	public String getPermissionCategoryName() {
		return permissionCategoryName;
	}

	public void setPermissionCategoryName(String permissionCategoryName) {
		this.permissionCategoryName = permissionCategoryName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
