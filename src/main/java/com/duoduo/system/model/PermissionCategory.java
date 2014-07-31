package com.duoduo.system.model;

import com.duoduo.core.model.IdEntity;

/**
 * 权限分类实体
 * @author chengesheng@gmail.com
 * @date 2014-5-30 上午13:49:29
 * @version 1.0.0
 */
public class PermissionCategory extends IdEntity {

	private static final long serialVersionUID = 7855793569562605313L;

	/** 备注 */
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
