package com.duoduo.security.vo;

/**
 * @author chengesheng@gmail.com
 * @date 2014-8-4 上午1:55:36
 * @version 1.0.0
 */
public class RoleResourceVO {

	private Long roleId;
	private Long resourceId;
	private String url;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
