package com.duoduo.system.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duoduo.core.vo.Page;
import com.duoduo.system.dao.RoleDao;
import com.duoduo.system.model.Role;

/**
 * 角色管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Service("roleManager")
public class RoleManagerImpl implements RoleManager {

	@Resource
	private RoleDao roleDao;

	@Override
	public Role getById(String id) {
		return roleDao.getById(id);
	}

	@Override
	public Role getByName(String name) {
		return roleDao.getByName(name);
	}

	@Override
	public Role create(Role role) {
		return roleDao.create(role);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public boolean delete(String id) {
		return roleDao.delete(id);
	}

	@Override
	public List<Role> listAll() {
		return roleDao.listAll();
	}

	@Override
	public List<Role> listByUserId(String userId) {
		return roleDao.listByUserId(userId);
	}

	@Override
	public Page<Role> pagingList(String name, Page<Role> page) {
		return roleDao.pagingList(name, page);
	}

}
