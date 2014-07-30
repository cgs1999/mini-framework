package com.duoduo.system.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.model.Permission;
import com.duoduo.system.model.PermissionCategory;
import com.duoduo.system.model.Role;
import com.duoduo.system.model.RolePermission;
import com.duoduo.system.model.User;
import com.duoduo.system.model.UserRole;

/**
 * 权限Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:35:55
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPermissionDao extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	private static Long categoryId1;
	private static Long categoryId2;

	@Resource
	private PermissionDao permissionDao;
	@Resource
	private PermissionCategoryDao permissionCategoryDao;

	// 以下用于测试listByUserId
	@Resource
	private RoleDao roleDao;
	@Resource
	private RolePermissionDao rolePermissionDao;
	@Resource
	private UserDao userDao;
	@Resource
	private UserRoleDao userRoleDao;

	@Test
	public void test10Create() {
		// 创建权限分类1
		PermissionCategory permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory1());
		Assert.assertNotNull(permissionCategory);
		categoryId1 = permissionCategory.getId();
		System.out.println("categoryId1=" + categoryId1);

		// 创建权限分类2
		permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory2());
		Assert.assertNotNull(permissionCategory);
		categoryId2 = permissionCategory.getId();
		System.out.println("categoryId2=" + categoryId2);

		// 创建权限1
		Permission permission = permissionDao.create(TestHelper.createPermission1(categoryId1));
		Assert.assertNotNull(permission);
		entityId1 = permission.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建权限2
		permission = permissionDao.create(TestHelper.createPermission2(categoryId2));
		Assert.assertNotNull(permission);
		entityId2 = permission.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		Permission permission = permissionDao.getById("" + entityId1);
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getId(), entityId1);

		permission = permissionDao.getById("" + entityId2);
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getId(), entityId2);
	}

	@Test
	public void test22GetByName() {
		Permission permission = permissionDao.getByName("创建记录");
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getName(), "创建记录");

		permission = permissionDao.getByName("删除记录");
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getName(), "删除记录");
	}

	@Test
	public void test24ListAll() {
		List<Permission> permissionList = permissionDao.listAll();
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 2);
	}

	@Test
	public void test26ListByCategory() {
		List<Permission> permissionList = permissionDao.listByCategory("" + categoryId1);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 1);
	}

	@Test
	public void test28ListByUserId() {
		// 创建用户1
		User user = userDao.create(TestHelper.createUser1());
		Assert.assertNotNull(user);
		Long userId1 = user.getId();
		System.out.println("userId1=" + userId1);

		// 创建用户2
		user = userDao.create(TestHelper.createUser2());
		Assert.assertNotNull(user);
		Long userId2 = user.getId();
		System.out.println("userId2=" + userId2);

		// 创建角色1
		Role role = roleDao.create(TestHelper.createRole1());
		Assert.assertNotNull(role);
		Long roleId1 = role.getId();
		System.out.println("roleId1=" + roleId1);

		// 创建角色2
		role = roleDao.create(TestHelper.createRole2());
		Assert.assertNotNull(role);
		Long roleId2 = role.getId();
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

		// 创建角色权限关系1
		RolePermission rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId1);
		rolePermission.setPermissionId(entityId1);
		Assert.assertTrue(rolePermissionDao.create(rolePermission));

		// 创建角色权限关系2
		rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId1);
		rolePermission.setPermissionId(entityId2);
		Assert.assertTrue(rolePermissionDao.create(rolePermission));

		// 创建角色权限关系3
		rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId2);
		rolePermission.setPermissionId(entityId2);
		Assert.assertTrue(rolePermissionDao.create(rolePermission));

		List<Permission> permissionList = permissionDao.listByUserId("" + userId1);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 2);
		System.out.println("User1's permission[" + TestHelper.getPermissionNames(permissionList) + "]");

		permissionList = permissionDao.listByUserId("" + userId2);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 1);
		System.out.println("User2's permission[" + TestHelper.getPermissionNames(permissionList) + "]");

		userDao.delete("" + userId1);
		userDao.delete("" + userId2);
		roleDao.delete("" + roleId1);
		roleDao.delete("" + roleId2);
		userRoleDao.deleteByUserId("" + userId1);
		userRoleDao.deleteByUserId("" + userId2);
		userRoleDao.deleteByRoleId("" + roleId1);
		userRoleDao.deleteByRoleId("" + roleId2);
		rolePermissionDao.deleteByRoleId("" + roleId1);
		rolePermissionDao.deleteByRoleId("" + roleId2);
		rolePermissionDao.deleteByPermissionId("" + entityId1);
		rolePermissionDao.deleteByPermissionId("" + entityId2);
	}

	@Test
	public void test29PageList() {
		Page<Permission> page = new Page<Permission>();
		page = permissionDao.pagingList("创建记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<Permission>();
		page = permissionDao.pagingList("删除记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<Permission>();
		page = permissionDao.pagingList("记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		page = new Page<Permission>();
		page = permissionDao.pagingList(null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);
	}

	@Test
	public void test30Update() {
		Permission permission = permissionDao.getById("" + entityId1);
		Assert.assertNotNull(permission);

		permission.setName("修改记录");
		permissionDao.update(permission);

		permission = permissionDao.getById("" + entityId1);
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getName(), "修改记录");
	}

	@Test
	public void test40Delete() {
		permissionDao.delete("" + entityId1);
		permissionDao.delete("" + entityId2);

		List<Permission> permissionList = permissionDao.listAll();
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 0);

		clearData();
	}

	private void clearData() {
		permissionCategoryDao.delete("" + categoryId1);
		permissionCategoryDao.delete("" + categoryId2);
	}
}
