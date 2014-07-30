package com.duoduo.system.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.model.Role;
import com.duoduo.system.model.User;
import com.duoduo.system.model.UserRole;

/**
 * 角色管理Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:34:46
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRoleDao extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao userDao;
	@Resource
	private UserRoleDao userRoleDao;

	@Test
	public void test00() {
		System.out.println(getClass());
	}

	@Test
	public void test10Create() {
		// 创建角色1
		Role role = roleDao.create(TestHelper.createRole1());

		Assert.assertNotNull(role);
		entityId1 = role.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建角色2
		role = roleDao.create(TestHelper.createRole2());

		Assert.assertNotNull(role);
		entityId2 = role.getId();
		System.out.println("entityId2=" + entityId2);

	}

	@Test
	public void test20GetById() {
		if (entityId1 != null) {
			Role role = roleDao.getById("" + entityId1);
			Assert.assertNotNull(role);
			Assert.assertEquals(role.getName(), "admin");
		} else {
			System.out.println("testGetById entityId is: " + entityId1);
		}
	}

	@Test
	public void test22GetByName() {
		Role role = roleDao.getByName("admin");
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getId(), entityId1);
	}

	@Test
	public void test24ListAll() {
		List<Role> roleList = roleDao.listAll();
		Assert.assertNotNull(roleList);
		Assert.assertEquals(roleList.size(), 2);

		printRoleList(roleList);
	}

	@Test
	public void test26ListByUserId() {
		// 创建用户
		User user = userDao.create(TestHelper.createUser1());

		Assert.assertNotNull(user);
		Long userId = user.getId();

		// 创建用户角色关系1
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(entityId1);
		userRoleDao.create(userRole);

		// 创建用户角色关系2
		userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(entityId2);
		userRoleDao.create(userRole);

		List<Role> roleList = roleDao.listByUserId("" + userId);
		Assert.assertNotNull(roleList);
		Assert.assertEquals(roleList.size(), 2);

		printRoleList(roleList);

		// 删除用户角色关系(共2条记录)
		userRoleDao.deleteByUserId("" + userId);

		// 删除用户(共1条记录)
		userDao.delete("" + userId);
	}

	@Test
	public void test28PageList() {
		// 账号作为关键字进行搜索
		Page<Role> page = new Page<Role>();
		page = roleDao.pagingList("admin", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);
	}

	@Test
	public void test30Update() {
		if (entityId1 != null) {
			Role role = roleDao.getById("" + entityId1);
			Assert.assertNotNull(role);

			role.setType("2");
			role.setMemo("修改后的备注信息");

			roleDao.update(role);

			role = roleDao.getById("" + entityId1);
			Assert.assertNotNull(role);
			Assert.assertEquals(role.getType(), "2");
		} else {
			System.out.println("testUpdate entityId is: " + entityId1);
		}
	}

	@Test
	public void test40Delete() {
		// 删除角色1
		if (entityId1 != null) {
			Role role = roleDao.getById("" + entityId1);
			Assert.assertNotNull(role);

			roleDao.delete("" + entityId1);

			role = roleDao.getById("" + entityId1);
			Assert.assertNull(role);

			entityId1 = null;
		} else {
			System.out.println("testDelete entityId is: " + entityId1);
		}

		// 删除角色2
		if (entityId2 != null) {
			Role role = roleDao.getById("" + entityId2);
			Assert.assertNotNull(role);

			roleDao.delete("" + entityId2);

			role = roleDao.getById("" + entityId2);
			Assert.assertNull(role);

			entityId2 = null;
		} else {
			System.out.println("testDelete entityId is: " + entityId2);
		}
	}

	private void printRoleList(List<Role> roleList) {
		for (Role role : roleList) {
			System.out.println("role[" + role.getId() + "]=" + role.toString());
		}
	}
}
