package com.duoduo.core.vo;

import net.sf.json.JSONObject;

import com.duoduo.core.model.IdEntity;

/**
 * 基础对象VO
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:28:09
 * @version 1.0.0
 */
public class BaseVO {

	/** 自增ID */
	private Long id;
	/** 名称 */
	private String name;
	/** 创建时间 */
	private String createTime;
	/** 最后更新时间 */
	private String updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || !getClass().getName().equals(obj.getClass().getName())) return false;

		IdEntity entity = (IdEntity) obj;
		return (hashCode() != 0 && entity.hashCode() != 0) ? hashCode() == entity.hashCode() : false;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
