package com.duoduo.system.manager;

import java.util.List;

import com.duoduo.core.vo.Page;
import com.duoduo.system.model.Permission;

/**
 * 权限管理业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:26:09
 * @version 1.0.0
 */
public interface PermissionManager {

	/**
	 * 根据Id获取权限
	 * @param id
	 * @return
	 */
	public Permission getById(String id);

	/**
	 * 根据名称获取权限
	 * @param name
	 * @return
	 */
	public Permission getByName(String name);

	/**
	 * 创建权限
	 * @param permission
	 * @return
	 */
	public Permission create(final Permission permission);

	/**
	 * 修改权限
	 * @param permission
	 */
	public void update(Permission permission);

	/**
	 * 删除权限
	 * @param id
	 * @return
	 */
	public boolean delete(String id);

	/**
	 * 获取所有权限列表
	 * @return
	 */
	public List<Permission> listAll();

	/**
	 * 根据用户id获取权限列表
	 * @return
	 */
	public List<Permission> listByUserId(String userId);

	/**
	 * 根据角色id获取权限列表
	 * @return
	 */
	public List<Permission> listByRoleId(String roleId);

	/**
	 * 分页查询权限列表（模糊查询，条件为：名称）
	 * @param name 名称
	 * @param page
	 * @return
	 */
	public Page<Permission> pagingList(String name, Page<Permission> page);
}
