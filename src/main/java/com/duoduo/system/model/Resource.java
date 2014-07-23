package com.duoduo.system.model;

import com.duoduo.core.model.IdEntity;

/**
 * 资源实体
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午12:47:45
 * @version 1.0.0
 */
public class Resource extends IdEntity {

	private static final long serialVersionUID = -8619608370246744651L;

	/** 资源名称 */
	private String name;
	/** 资源类型，菜单和操作 */
	private String type;
	/** 链接地址 */
	private String url;
	/** 所属父资源ID */
	private Long parentId;
	/** 所有父资源ID，以半角逗号分隔 */
	private String parentIds;
	/** 排序编号，值越大越靠后 */
	private Integer orderIndex;
	/** 启停状态 */
	private Boolean enable;

	/** 上级菜单名称 */
	private String parentName;

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

}
