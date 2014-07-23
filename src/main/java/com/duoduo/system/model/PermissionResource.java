package com.duoduo.system.model;

/**
 * 权限资源关系
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午12:53:26
 * @version 1.0.0
 */
public class PermissionResource {

	/** 权限ID */
	private Long permissionId;

	/** 资源ID */
	private Long resourceId;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
