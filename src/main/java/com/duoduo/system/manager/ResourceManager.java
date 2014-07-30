package com.duoduo.system.manager;

import java.util.List;

import com.duoduo.core.vo.Page;
import com.duoduo.system.model.Resource;

/**
 * 资源管理业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:26:09
 * @version 1.0.0
 */
public interface ResourceManager {

	/**
	 * 根据Id获取资源
	 * @param id
	 * @return
	 */
	public Resource getById(String id);

	/**
	 * 根据名称获取资源
	 * @param name
	 * @return
	 */
	public Resource getByName(String name);

	/**
	 * 创建资源
	 * @param resource
	 * @return
	 */
	public Resource create(final Resource resource);

	/**
	 * 修改资源
	 * @param resource
	 */
	public void update(Resource resource);

	/**
	 * 删除资源
	 * @param id
	 * @return
	 */
	public boolean delete(String id);

	/**
	 * 分页查询资源列表（模糊查询，条件为：名称）
	 * @param name 名称
	 * @param page
	 * @return
	 */
	public Page<Resource> pagingList(String name, Page<Resource> page);

	/**
	 * 获取用户所拥有的资源
	 * @return
	 */
	public List<Resource> listByUserId(String userId);

	/**
	 * 根据角色编号获取资源
	 * @param roleId
	 */
	public List<Resource> listByRoleId(String roleId);

	/**
	 * 根据权限编号获取资源
	 * @param permissionId
	 */
	public List<Resource> listByPermissionId(String permissionId);

	/**
	 * 根据父资源编号获取所属子资源
	 * @param parentId
	 * @return
	 */
	public List<Resource> listSubResource(String parentId);

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Resource> listAllMenu();

	/**
	 * 获取所有一级菜单
	 * @return
	 */
	public List<Resource> listRootMenu();
}
