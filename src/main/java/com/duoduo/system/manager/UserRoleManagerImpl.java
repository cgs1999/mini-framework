package com.duoduo.system.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.duoduo.system.dao.UserRoleDao;
import com.duoduo.system.model.UserRole;

/**
 * 用户角色关系实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:54:15
 * @version 1.0.0
 */
@Transactional
@Service("userRoleManager")
public class UserRoleManagerImpl implements UserRoleManager {

	@Resource
	private UserRoleDao userRoleDao;

	@Override
	public boolean saveOrUpdateUserRoles(String userId, String roleIds) {
		if (!StringUtils.hasText(userId)) {
			return true;
		}

		// 保存或更新前先删除用户原有的角色
		deleteByUserId(userId);

		// 保存用户角色信息
		if (roleIds != null && !"".equals(roleIds)) {
			String[] ids = roleIds.split(",");
			for (int i = 0, len = ids.length; i < len; i++) {
				UserRole userRole = new UserRole();
				userRole.setUserId(Long.valueOf(userId));
				userRole.setRoleId(Long.valueOf(ids[i]));

				userRoleDao.create(userRole);
			}
		}

		return true;
	}

	@Override
	public boolean deleteByUserId(String userId) {
		return userRoleDao.deleteByUserId(userId);
	}

	@Override
	public boolean deleteByRoleId(String roleId) {
		return userRoleDao.deleteByRoleId(roleId);
	}

}
