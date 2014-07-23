package com.duoduo.system.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.duoduo.system.dao.PermissionResourceDao;
import com.duoduo.system.model.PermissionResource;

/**
 * 角色权限关系实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:54:15
 * @version 1.0.0
 */
@Transactional
@Service("permissionResourceManager")
public class PermissionResourceManagerImpl implements PermissionResourceManager {

	@Resource
	private PermissionResourceDao permissionResourceDao;

	@Override
	public boolean saveOrUpdate(String permissionId, String resourceIds) {
		if (!StringUtils.hasText(permissionId)) {
			return true;
		}

		// 保存或更新前先删除用户原有的角色
		deleteByResourceId(permissionId);

		// 保存用户角色信息
		if (resourceIds != null && !"".equals(resourceIds)) {
			String[] ids = resourceIds.split(",");
			for (int i = 0, len = ids.length; i < len; i++) {
				PermissionResource permissionResource = new PermissionResource();
				permissionResource.setPermissionId(Long.valueOf(permissionId));
				permissionResource.setResourceId(Long.valueOf(ids[i]));

				permissionResourceDao.create(permissionResource);
			}
		}

		return true;
	}

	@Override
	public boolean deleteByPermissionId(String permissionId) {
		return permissionResourceDao.deleteByPermissionId(permissionId);
	}

	@Override
	public boolean deleteByResourceId(String resourceId) {
		return permissionResourceDao.deleteByResourceId(resourceId);
	}

}
