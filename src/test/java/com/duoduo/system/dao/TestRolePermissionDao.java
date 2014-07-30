package com.duoduo.system.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.system.model.Permission;
import com.duoduo.system.model.PermissionCategory;
import com.duoduo.system.model.Role;
import com.duoduo.system.model.RolePermission;

/**
 * 角色权限关系Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:36:27
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRolePermissionDao extends BaseTest {

	private static Long roleId1;
	private static Long roleId2;

	private static Long categoryId;

	private static Long permissionId1;
	private static Long permissionId2;

	@Resource
	private RolePermissionDao rolePermissionDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private PermissionCategoryDao permissionCategoryDao;
	@Resource
	private PermissionDao permissionDao;

	@Test
	public void test10Create() {
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

		// 创建权限分类
		PermissionCategory permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory1());
		Assert.assertNotNull(permissionCategory);
		categoryId = permissionCategory.getId();
		System.out.println("categoryId=" + categoryId);

		// 创建权限1
		Permission permission = permissionDao.create(TestHelper.createPermission1(categoryId));
		Assert.assertNotNull(permission);
		permissionId1 = permission.getId();
		System.out.println("permissionId1=" + permissionId1);

		// 创建权限2
		permission = permissionDao.create(TestHelper.createPermission2(categoryId));
		Assert.assertNotNull(permission);
		permissionId2 = permission.getId();
		System.out.println("permissionId2=" + permissionId2);

		// 创建角色权限关系1
		RolePermission rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId1);
		rolePermission.setPermissionId(permissionId1);
		Assert.assertTrue(rolePermissionDao.create(rolePermission));

		// 创建角色权限关系2
		rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId1);
		rolePermission.setPermissionId(permissionId2);
		Assert.assertTrue(rolePermissionDao.create(rolePermission));

		// 创建角色权限关系3
		rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId2);
		rolePermission.setPermissionId(permissionId2);
		Assert.assertTrue(rolePermissionDao.create(rolePermission));
	}

	@Test
	public void test20ListByRoleId() {
		// role1
		List<RolePermission> rolePermissionList = rolePermissionDao.listByRoleId("" + roleId1);
		Assert.assertNotNull(rolePermissionList);
		Assert.assertEquals(rolePermissionList.size(), 2);

		// role2
		rolePermissionList = rolePermissionDao.listByRoleId("" + roleId2);
		Assert.assertNotNull(rolePermissionList);
		Assert.assertEquals(rolePermissionList.size(), 1);
	}

	@Test
	public void test22ListByPermissionId() {
		// permission1
		List<RolePermission> rolePermissionList = rolePermissionDao.listByPermissionId("" + permissionId1);
		Assert.assertNotNull(rolePermissionList);
		Assert.assertEquals(rolePermissionList.size(), 1);

		// permission2
		rolePermissionList = rolePermissionDao.listByPermissionId("" + permissionId2);
		Assert.assertNotNull(rolePermissionList);
		Assert.assertEquals(rolePermissionList.size(), 2);
	}

	@Test
	public void test30DeleteByRoleId() {
		Assert.assertTrue(rolePermissionDao.deleteByRoleId("" + roleId1));

		// role1
		List<RolePermission> rolePermissionList = rolePermissionDao.listByRoleId("" + roleId1);
		Assert.assertNotNull(rolePermissionList);
		Assert.assertEquals(rolePermissionList.size(), 0);
	}

	@Test
	public void test32DeleteByPermissionId() {
		Assert.assertTrue(rolePermissionDao.deleteByPermissionId("" + permissionId2));

		// role1
		List<RolePermission> rolePermissionList = rolePermissionDao.listByPermissionId("" + permissionId2);
		Assert.assertNotNull(rolePermissionList);
		Assert.assertEquals(rolePermissionList.size(), 0);

		clearData();
	}

	private void clearData() {
		roleDao.delete("" + roleId1);
		roleDao.delete("" + roleId2);
		permissionCategoryDao.delete("" + categoryId);
		permissionDao.delete("" + permissionId1);
		permissionDao.delete("" + permissionId2);
	}
}
