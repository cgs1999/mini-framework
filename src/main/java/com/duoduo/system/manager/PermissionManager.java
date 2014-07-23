package com.duoduo.system.manager;

import java.util.List;

import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.PermissionVO;

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
	public PermissionVO getById(String id);

	/**
	 * 根据名称获取权限
	 * @param name
	 * @return
	 */
	public PermissionVO getByName(String name);

	/**
	 * 创建权限
	 * @param permissionVO
	 * @return
	 */
	public PermissionVO create(final PermissionVO permissionVO);

	/**
	 * 修改权限
	 * @param permissionVO
	 */
	public void update(PermissionVO permissionVO);

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
	public List<PermissionVO> listAll();

	/**
	 * 根据用户id获取权限列表
	 * @return
	 */
	public List<PermissionVO> listByUserId(String userId);

	/**
	 * 分页查询权限列表（模糊查询，条件为：名称）
	 * @param name 名称
	 * @param page
	 * @return
	 */
	public Page<PermissionVO> pagingList(String name, Page<PermissionVO> page);
}
