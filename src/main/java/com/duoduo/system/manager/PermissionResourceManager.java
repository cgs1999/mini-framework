package com.duoduo.system.manager;

/**
 * 权限资源关系业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:45:27
 * @version 1.0.0
 */
public interface PermissionResourceManager {

	/**
	 * 保存或更新权限资源信息
	 * @param permissionId 权限id
	 * @param resourceIds 权限资源ids，以半角逗号分隔
	 * @return
	 */
	public boolean saveOrUpdate(String permissionId, String resourceIds);

	/**
	 * 根据权限id删除权限资源信息
	 * @param permissionId 权限id
	 * @return
	 */
	public boolean deleteByPermissionId(String permissionId);

	/**
	 * 根据资源id删除权限资源信息
	 * @param resourceId 资源id
	 * @return
	 */
	public boolean deleteByResourceId(String resourceId);
}
