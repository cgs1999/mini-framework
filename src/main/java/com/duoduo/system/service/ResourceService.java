package com.duoduo.system.service;

import java.util.List;

import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.ResourceVO;

/**
 * 资源管理业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:26:09
 * @version 1.0.0
 */
public interface ResourceService {

	/**
	 * 根据Id获取资源
	 * @param id
	 * @return
	 */
	public ResourceVO getById(String id);

	/**
	 * 根据名称获取资源
	 * @param name
	 * @return
	 */
	public ResourceVO getByName(String name);

	/**
	 * 创建资源
	 * @param resourceVO
	 * @return
	 */
	public ResourceVO create(final ResourceVO resourceVO);

	/**
	 * 修改资源
	 * @param resourceVO
	 */
	public void update(ResourceVO resourceVO);

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
	public Page<ResourceVO> pagingList(String name, Page<ResourceVO> page);

	/**
	 * 获取用户所拥有的资源
	 * @return
	 */
	public List<ResourceVO> listByUserId(String userId);

	/**
	 * 根据角色编号获取资源
	 * @param roleId
	 */
	public List<ResourceVO> listByRoleId(String roleId);

	/**
	 * 根据权限编号获取资源
	 * @param permissionId
	 */
	public List<ResourceVO> listByPermissionId(String permissionId);

	/**
	 * 根据父资源编号获取所属子资源
	 * @param parentId
	 * @return
	 */
	public List<ResourceVO> listSubResource(String parentId);

	/**
	 * 获取所有菜单，用于主界面显示菜单，特别需要注意：返回的VO只有id,parentId,name,url
	 * @return
	 */
	public List<ResourceVO> listAllMenuSimple();

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<ResourceVO> listAllMenu();

	/**
	 * 获取所有一级菜单
	 * @return
	 */
	public List<ResourceVO> listRootMenu();
}
