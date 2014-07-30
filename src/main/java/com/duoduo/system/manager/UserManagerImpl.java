package com.duoduo.system.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duoduo.core.vo.Page;
import com.duoduo.system.dao.UserDao;
import com.duoduo.system.model.User;

/**
 * 用户管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Service("userManager")
public class UserManagerImpl implements UserManager {

	@Resource
	private UserDao userDao;
	@Resource
	private UserRoleManager userRoleManager;

	@Override
	public User getById(String id) {
		return userDao.getById(id);
	}

	@Override
	public User getByAccount(String account) {
		return userDao.getByAccount(account);
	}

	@Override
	public User create(User user) {
		return userDao.create(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public boolean delete(String id) {
		return userDao.delete(id);
	}

	@Override
	public Page<User> pagingList(String account, String name, String email, String phone, Page<User> page) {
		return userDao.pagingList(account, name, email, phone, page);
	}

}
