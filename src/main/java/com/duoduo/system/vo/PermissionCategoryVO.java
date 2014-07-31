package com.duoduo.system.vo;

import com.duoduo.core.util.DateUtils;
import com.duoduo.core.vo.BaseVO;
import com.duoduo.system.model.PermissionCategory;

/**
 * 权限分类VO
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午11:15:55
 * @version 1.0.0
 */
public class PermissionCategoryVO extends BaseVO {

	/** 备注 */
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 转换Entity为VO
	 * @param entity
	 * @return
	 */
	public static PermissionCategoryVO fromEntity(PermissionCategory entity) {
		PermissionCategoryVO vo = new PermissionCategoryVO();
		vo.setId(entity.getId());
		vo.setName(entity.getName());
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
	public static PermissionCategory toEntity(PermissionCategoryVO vo) {
		PermissionCategory entity = new PermissionCategory();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setMemo(vo.getMemo());
		return entity;
	}
}
