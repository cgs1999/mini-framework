package com.duoduo.system.manager;

/**
 * 角色权限关系业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:45:27
 * @version 1.0.0
 */
public interface RolePermissionManager {

	/**
	 * 保存或更新角色权限信息
	 * @param roleId 角色id
	 * @param permissionIds 角色权限ids，以半角逗号分隔
	 * @return
	 */
	public boolean saveOrUpdate(String roleId, String permissionIds);

	/**
	 * 根据角色id删除角色权限信息
	 * @param roleId 角色id
	 * @return
	 */
	public boolean deleteByRoleId(String roleId);

	/**
	 * 根据权限id删除角色权限信息
	 * @param permissionId 权限id
	 * @return
	 */
	public boolean deleteByPermissionId(String permissionId);
}
