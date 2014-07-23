package com.duoduo.system.manager;

/**
 * 用户角色关系业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:45:27
 * @version 1.0.0
 */
public interface UserRoleManager {

	/**
	 * 保存或更新用户角色信息
	 * @param userId 用户id
	 * @param roleIds 用户角色id，以半角逗号分隔
	 * @return
	 */
	public boolean saveOrUpdateUserRoles(String userId, String roleIds);

	/**
	 * 根据用户id删除用户角色信息
	 * @param userId 用户id
	 * @return
	 */
	public boolean deleteByUserId(String userId);

	/**
	 * 根据角色id删除用户角色信息
	 * @param roleId 角色id
	 * @return
	 */
	public boolean deleteByRoleId(String roleId);
}
