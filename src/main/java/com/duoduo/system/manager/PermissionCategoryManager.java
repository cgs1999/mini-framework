package com.duoduo.system.manager;

import java.util.List;

import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.PermissionCategoryVO;

/**
 * 权限分类管理业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:26:09
 * @version 1.0.0
 */
public interface PermissionCategoryManager {

	/**
	 * 根据Id获取权限分类
	 * @param id
	 * @return
	 */
	public PermissionCategoryVO getById(String id);

	/**
	 * 根据名称获取权限分类
	 * @param name
	 * @return
	 */
	public PermissionCategoryVO getByName(String name);

	/**
	 * 创建权限分类
	 * @param permissionCategoryVO
	 * @return
	 */
	public PermissionCategoryVO create(final PermissionCategoryVO permissionCategoryVO);

	/**
	 * 修改权限分类
	 * @param permissionCategoryVO
	 */
	public void update(PermissionCategoryVO permissionCategoryVO);

	/**
	 * 删除权限分类
	 * @param id
	 * @return
	 */
	public boolean delete(String id);

	/**
	 * 获取所有权限分类列表
	 * @return
	 */
	public List<PermissionCategoryVO> listAll();

	/**
	 * 分页查询权限分类列表（模糊查询，条件为：名称）
	 * @param name 名称
	 * @param page
	 * @return
	 */
	public Page<PermissionCategoryVO> pagingList(String name, Page<PermissionCategoryVO> page);
}
