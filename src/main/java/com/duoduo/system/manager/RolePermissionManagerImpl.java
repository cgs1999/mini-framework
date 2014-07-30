package com.duoduo.system.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.duoduo.system.dao.RolePermissionDao;
import com.duoduo.system.model.RolePermission;

/**
 * 角色权限关系实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:54:15
 * @version 1.0.0
 */
@Service("rolePermissionManager")
public class RolePermissionManagerImpl implements RolePermissionManager {

	@Resource
	private RolePermissionDao rolePermissionDao;

	@Override
	public boolean saveOrUpdate(String roleId, String permissionIds) {
		if (!StringUtils.hasText(roleId)) {
			return true;
		}

		// 保存或更新前先删除用户原有的角色
		deleteByRoleId(roleId);

		// 保存用户角色信息
		if (permissionIds != null && !"".equals(permissionIds)) {
			String[] ids = permissionIds.split(",");
			for (int i = 0, len = ids.length; i < len; i++) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setRoleId(Long.valueOf(roleId));
				rolePermission.setPermissionId(Long.valueOf(ids[i]));

				rolePermissionDao.create(rolePermission);
			}
		}

		return true;
	}

	@Override
	public boolean deleteByRoleId(String roleId) {
		return rolePermissionDao.deleteByRoleId(roleId);
	}

	@Override
	public boolean deleteByPermissionId(String permissionId) {
		return rolePermissionDao.deleteByPermissionId(permissionId);
	}

}
