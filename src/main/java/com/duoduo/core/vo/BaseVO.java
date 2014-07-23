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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
