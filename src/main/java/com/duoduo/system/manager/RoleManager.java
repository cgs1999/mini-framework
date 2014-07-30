package com.duoduo.system.manager;

import java.util.List;

import com.duoduo.core.vo.Page;
import com.duoduo.system.model.Role;

/**
 * 角色管理业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:26:09
 * @version 1.0.0
 */
public interface RoleManager {

	/**
	 * 根据Id获取角色
	 * @param id
	 * @return
	 */
	public Role getById(String id);

	/**
	 * 根据名称获取角色
	 * @param name
	 * @return
	 */
	public Role getByName(String name);

	/**
	 * 创建角色
	 * @param role
	 * @return
	 */
	public Role create(final Role role);

	/**
	 * 修改角色
	 * @param role
	 */
	public void update(Role role);

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	public boolean delete(String id);

	/**
	 * 根据所有角色列表
	 * @return
	 */
	public List<Role> listAll();

	/**
	 * 根据用户id获取角色列表
	 * @return
	 */
	public List<Role> listByUserId(String userId);

	/**
	 * 分页查询角色列表（模糊查询，条件为：名称）
	 * @param name 名称
	 * @param page
	 * @return
	 */
	public Page<Role> pagingList(String name, Page<Role> page);
}
