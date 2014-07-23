package com.duoduo.core.model;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;

/**
 * 以长整型ID为主键的实体
 * @author chengesheng@gmail.com
 * @date 2014-6-17 上午11:05:12
 * @version 1.0.0
 */
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -1172752628626170213L;

	private Serializable id;
	/** 创建时间 */
	private Date createTime;
	/** 最后更新时间 */
	private Date updateTime;

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		BaseEntity other = (BaseEntity) obj;
		if (getId() == null) {
			if (other.getId() != null) return false;
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}
