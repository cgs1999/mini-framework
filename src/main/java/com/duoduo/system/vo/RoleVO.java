package com.duoduo.system.vo;

import com.duoduo.core.util.DateUtils;
import com.duoduo.core.vo.BaseVO;
import com.duoduo.system.model.Role;

/**
 * 角色VO
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午11:15:55
 * @version 1.0.0
 */
public class RoleVO extends BaseVO {

	/** 角色名称 */
	private String name;
	/** 角色类型，系统和自定义 */
	private String type;
	/** 启停状态 */
	private Boolean enable;
	/** 备注 */
	private String memo;
	/** 创建时间 */
	private String createTime;
	/** 最后更新时间 */
	private String updateTime;

	// 资源信息
	private String resourceIds;
	private String resourceNames;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
	public static RoleVO fromEntity(Role entity) {
		RoleVO vo = new RoleVO();
		vo.setId(entity.getId());
		vo.setName(entity.getName());
		vo.setType(entity.getType());
		vo.setEnable(entity.getEnable());
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
	public static Role toEntity(RoleVO vo) {
		Role entity = new Role();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setType(vo.getType());
		entity.setEnable(vo.getEnable());
		entity.setMemo(vo.getMemo());
		return entity;
	}
}
