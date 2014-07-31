package com.duoduo.system.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duoduo.core.vo.Page;
import com.duoduo.system.dao.PermissionDao;
import com.duoduo.system.model.Permission;

/**
 * 权限管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Service("permissionManager")
public class PermissionManagerImpl implements PermissionManager {

	@Resource
	private PermissionDao permissionDao;

	@Override
	public Permission getById(String id) {
		return permissionDao.getById(id);
	}

	@Override
	public Permission getByName(String name) {
		return permissionDao.getByName(name);
	}

	@Override
	public Permission create(Permission permission) {
		return permissionDao.create(permission);
	}

	@Override
	public void update(Permission permission) {
		permissionDao.update(permission);
	}

	@Override
	public boolean delete(String id) {
		return permissionDao.delete(id);
	}

	@Override
	public List<Permission> listAll() {
		return permissionDao.listAll();
	}

	@Override
	public List<Permission> listByCategory(String categoryId) {
		return permissionDao.listByCategory(categoryId);
	}

	@Override
	public List<Permission> listByUserId(String userId) {
		return permissionDao.listByUserId(userId);
	}

	@Override
	public List<Permission> listByRoleId(String roleId) {
		return permissionDao.listByRoleId(roleId);
	}

	@Override
	public Page<Permission> pagingList(String name, Page<Permission> page) {
		return permissionDao.pagingList(name, page);
	}

}
