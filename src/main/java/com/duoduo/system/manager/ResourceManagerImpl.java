package com.duoduo.system.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoduo.core.vo.Page;
import com.duoduo.system.Constants;
import com.duoduo.system.dao.ResourceDao;
import com.duoduo.system.model.Resource;

/**
 * 资源管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Service("resourceManager")
public class ResourceManagerImpl implements ResourceManager {

	private static final String TYPE_MENU = "1";

	@javax.annotation.Resource
	private ResourceDao resourceDao;

	@Override
	public Resource getById(String id) {
		return resourceDao.getById(id);
	}

	@Override
	public Resource getByName(String name) {
		return resourceDao.getByName(name);
	}

	@Override
	public Resource create(Resource resource) {
		return resourceDao.create(resource);
	}

	@Override
	public void update(Resource resource) {
		resourceDao.update(resource);
	}

	@Override
	public boolean delete(String id) {
		return resourceDao.delete(id);
	}

	@Override
	public Page<Resource> pagingList(String name, Page<Resource> page) {
		return resourceDao.pagingList(name, page);
	}

	@Override
	public List<Resource> listByUserId(String userId) {
		return resourceDao.listByUserId(userId);
	}

	@Override
	public List<Resource> listByRoleId(String roleId) {
		return resourceDao.listByRoleId(roleId);
	}

	@Override
	public List<Resource> listByPermissionId(String permissionId) {
		return resourceDao.listByPermissionId(permissionId);
	}

	@Override
	public List<Resource> listSubResource(String parentId) {
		return resourceDao.listSubResource(parentId);
	}

	@Override
	public List<Resource> listAllMenu() {
		return resourceDao.listByType(TYPE_MENU);
	}

	@Override
	public List<Resource> listRootMenu() {
		return resourceDao.listSubResourceByType("" + Constants.ROOT_MENU_ID, TYPE_MENU);
	}
}
