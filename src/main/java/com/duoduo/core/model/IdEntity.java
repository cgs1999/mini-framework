package com.duoduo.core.model;

/**
 * 以长整型ID为主键的实体
 * @author chengesheng@gmail.com
 * @date 2014-6-17 上午11:05:12
 * @version 1.0.0
 */
public abstract class IdEntity extends BaseEntity {

	private static final long serialVersionUID = -767861783986383982L;

	private Long id;
	/** 名称 */
	private String name;

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

}
