package com.duoduo.system.service;

import java.util.List;

import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.RoleVO;

/**
 * 角色管理业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:26:09
 * @version 1.0.0
 */
public interface RoleService {

	/**
	 * 根据Id获取角色
	 * @param id
	 * @return
	 */
	public RoleVO getById(String id);

	/**
	 * 根据名称获取角色
	 * @param name
	 * @return
	 */
	public RoleVO getByName(String name);

	/**
	 * 创建角色
	 * @param roleVO
	 * @return
	 */
	public RoleVO create(final RoleVO roleVO);

	/**
	 * 修改角色
	 * @param roleVO
	 */
	public void update(RoleVO roleVO);

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
	public List<RoleVO> listAll();

	/**
	 * 根据用户id获取角色列表
	 * @return
	 */
	public List<RoleVO> listByUserId(String userId);

	/**
	 * 分页查询角色列表（模糊查询，条件为：名称）
	 * @param name 名称
	 * @param page
	 * @return
	 */
	public Page<RoleVO> pagingList(String name, Page<RoleVO> page);
}
