package com.duoduo.core.model;


/**
 * 以UUID字符串为主键的实体
 * @author chengesheng@gmail.com
 * @date 2014-6-17 上午11:08:27
 * @version 1.0.0
 */
public abstract class UuidEntity extends BaseEntity {

	private static final long serialVersionUID = -2085799399310041952L;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
