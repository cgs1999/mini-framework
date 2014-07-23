package com.duoduo.system.vo;

import com.duoduo.core.util.DateUtils;
import com.duoduo.core.vo.BaseVO;
import com.duoduo.system.Constants;
import com.duoduo.system.model.Resource;

/**
 * 资源VO
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:27:24
 * @version 1.0.0
 */
public class ResourceVO extends BaseVO {

	/** 资源名称 */
	private String name;
	/** 资源类型，菜单和操作 */
	private String type;
	/** 链接地址 */
	private String url;
	/** 所属父资源ID */
	private Long parentId;
	/** 上级菜单名称 */
	private String parentName;
	/** 所有父资源ID，以半角逗号分隔 */
	private String parentIds;
	/** 排序索引 */
	private Integer orderIndex;
	/** 启停状态 */
	private Boolean enable;
	/** 创建时间 */
	private String createTime;
	/** 最后更新时间 */
	private String updateTime;

	// 上级菜单id, 用于treegrid的展示
	private Long _parentId = null;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
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

	public Long get_parentId() {
		return _parentId;
	}

	public void set_parentId(Long _parentId) {
		this._parentId = _parentId;
	}

	/**
	 * 转换Entity为VO
	 * @param entity
	 * @return
	 */
	public static ResourceVO fromEntity(Resource entity) {
		ResourceVO vo = new ResourceVO();
		vo.setId(entity.getId());
		vo.setName(entity.getName());
		vo.setType(entity.getType());
		vo.setUrl(entity.getUrl());
		vo.setParentId(entity.getParentId());
		vo.setParentIds(entity.getParentIds());
		vo.setOrderIndex(entity.getOrderIndex());
		vo.setEnable(entity.getEnable());
		vo.setCreateTime(DateUtils.toDatetimeString(entity.getCreateTime()));
		vo.setUpdateTime(DateUtils.toDatetimeString(entity.getUpdateTime()));

		// 设置_parentId
		if (entity.getParentId() != Constants.ROOT_MENU_ID) {
			vo.set_parentId(entity.getParentId());
		}
		return vo;
	}

	/**
	 * 转换VO为Entity
	 * @param vo
	 * @return
	 */
	public static Resource toEntity(ResourceVO vo) {
		Resource entity = new Resource();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setType(vo.getType());
		entity.setUrl(vo.getUrl());
		entity.setParentId(vo.getParentId());
		entity.setParentIds(vo.getParentIds());
		entity.setOrderIndex(vo.getOrderIndex());
		entity.setEnable(vo.getEnable());
		return entity;
	}

	/**
	 * 转换Entity为简单VO
	 * @param entity
	 * @return
	 */
	public static ResourceVO toSimpleVO(Resource entity) {
		ResourceVO vo = new ResourceVO();
		vo.setId(entity.getId());
		vo.setName(entity.getName());
		vo.setUrl(entity.getUrl());
		vo.setParentId(entity.getParentId());

		// 设置_parentId
		if (entity.getParentId() != Constants.ROOT_MENU_ID) {
			vo.set_parentId(entity.getParentId());
		}
		return vo;
	}
}
