package com.duoduo.system.vo;

import com.duoduo.core.util.DateUtils;
import com.duoduo.core.vo.BaseVO;
import com.duoduo.system.model.Permission;

/**
 * 权限VO
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午11:15:55
 * @version 1.0.0
 */
public class PermissionVO extends BaseVO {

	/** 权限分类Id */
	private Long permissionCategoryId;
	/** 权限分类名称 */
	private String permissionCategoryName;
	/** 备注 */
	private String memo;

	// 资源信息
	private String resourceIds;
	private String resourceNames;

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

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}

	/**
	 * 转换Entity为VO
	 * @param entity
	 * @return
	 */
	public static PermissionVO fromEntity(Permission entity) {
		PermissionVO vo = new PermissionVO();
		vo.setId(entity.getId());
		vo.setName(entity.getName());
		vo.setPermissionCategoryId(entity.getPermissionCategoryId());
		vo.setPermissionCategoryName(entity.getPermissionCategoryName());
		vo.setMemo(entity.getMemo());
		vo.setCreateTime(DateUtils.toDatetimeString(entity.getCreateTime()));
		vo.setUpdateTime(DateUtils.toDatetimeString(entity.getUpdateTime()));
		return vo;
	}

	/**
	 * 转换VO为Entity
	 * @param vo
	 * @return
	 */
	public static Permission toEntity(PermissionVO vo) {
		Permission entity = new Permission();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setPermissionCategoryId(vo.getPermissionCategoryId());
		entity.setMemo(vo.getMemo());
		return entity;
	}
}
