package com.duoduo.system.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.system.model.Role;
import com.duoduo.system.model.User;
import com.duoduo.system.model.UserRole;

/**
 * 用户角色关系Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-22 下午2:14:19
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRoleDao extends BaseTest {

	private static Long userId1;
	private static Long userId2;

	private static Long roleId1;
	private static Long roleId2;

	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;

	@Test
	public void test10Create() {
		// 创建用户1
		User user = userDao.create(TestHelper.createUser1());
		Assert.assertNotNull(user);
		userId1 = user.getId();
		System.out.println("userId1=" + userId1);

		// 创建用户2
		user = userDao.create(TestHelper.createUser2());
		Assert.assertNotNull(user);
		userId2 = user.getId();
		System.out.println("userId2=" + userId2);

		// 创建角色1
		Role role = roleDao.create(TestHelper.createRole1());
		Assert.assertNotNull(role);
		roleId1 = role.getId();
		System.out.println("roleId1=" + roleId1);

		// 创建角色2
		role = roleDao.create(TestHelper.createRole2());
		Assert.assertNotNull(role);
		roleId2 = role.getId();
		System.out.println("roleId2=" + roleId2);

		// 创建用户角色关系1
		UserRole userRole = new UserRole();
		userRole.setUserId(userId1);
		userRole.setRoleId(roleId1);
		Assert.assertTrue(userRoleDao.create(userRole));

		// 创建用户角色关系2
		userRole = new UserRole();
		userRole.setUserId(userId1);
		userRole.setRoleId(roleId2);
		Assert.assertTrue(userRoleDao.create(userRole));

		// 创建用户角色关系3
		userRole = new UserRole();
		userRole.setUserId(userId2);
		userRole.setRoleId(roleId2);
		Assert.assertTrue(userRoleDao.create(userRole));
	}

	@Test
	public void test20ListByUserId() {
		// user1
		List<UserRole> userRoleList = userRoleDao.listByUserId("" + userId1);
		Assert.assertNotNull(userRoleList);
		Assert.assertEquals(userRoleList.size(), 2);

		// user2
		userRoleList = userRoleDao.listByUserId("" + userId2);
		Assert.assertNotNull(userRoleList);
		Assert.assertEquals(userRoleList.size(), 1);
	}

	@Test
	public void test22ListByRoleId() {
		// role1
		List<UserRole> userRoleList = userRoleDao.listByRoleId("" + roleId1);
		Assert.assertNotNull(userRoleList);
		Assert.assertEquals(userRoleList.size(), 1);

		// role2
		userRoleList = userRoleDao.listByRoleId("" + roleId2);
		Assert.assertNotNull(userRoleList);
		Assert.assertEquals(userRoleList.size(), 2);
	}

	@Test
	public void test30DeleteByUserId() {
		Assert.assertTrue(userRoleDao.deleteByUserId("" + userId1));

		// user1
		List<UserRole> userRoleList = userRoleDao.listByUserId("" + userId1);
		Assert.assertNotNull(userRoleList);
		Assert.assertEquals(userRoleList.size(), 0);
	}

	@Test
	public void test32DeleteByRoleId() {
		Assert.assertTrue(userRoleDao.deleteByRoleId("" + roleId2));

		// user1
		List<UserRole> userRoleList = userRoleDao.listByRoleId("" + roleId2);
		Assert.assertNotNull(userRoleList);
		Assert.assertEquals(userRoleList.size(), 0);

		clearData();
	}

	private void clearData() {
		userDao.delete("" + userId1);
		userDao.delete("" + userId2);
		roleDao.delete("" + roleId1);
		roleDao.delete("" + roleId2);
	}
}
