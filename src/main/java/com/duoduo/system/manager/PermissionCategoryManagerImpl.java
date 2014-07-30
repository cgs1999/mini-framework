package com.duoduo.system.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duoduo.core.vo.Page;
import com.duoduo.system.dao.PermissionCategoryDao;
import com.duoduo.system.model.PermissionCategory;

/**
 * 权限分类管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Service("permissionCategoryManager")
public class PermissionCategoryManagerImpl implements PermissionCategoryManager {

	@Resource
	private PermissionCategoryDao permissionCategoryDao;

	@Override
	public PermissionCategory getById(String id) {
		return permissionCategoryDao.getById(id);
	}

	@Override
	public PermissionCategory getByName(String name) {
		return permissionCategoryDao.getByName(name);
	}

	@Override
	public PermissionCategory create(PermissionCategory permissionCategory) {
		return permissionCategoryDao.create(permissionCategory);
	}

	@Override
	public void update(PermissionCategory permissionCategory) {
		permissionCategoryDao.update(permissionCategory);
	}

	@Override
	public boolean delete(String id) {
		return permissionCategoryDao.delete(id);
	}

	@Override
	public List<PermissionCategory> listAll() {
		return permissionCategoryDao.listAll();
	}

	@Override
	public Page<PermissionCategory> pagingList(String name, Page<PermissionCategory> page) {
		return permissionCategoryDao.pagingList(name, page);
	}

}
