package com.duoduo.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.PermissionCategoryVO;
import com.duoduo.system.vo.PermissionVO;
import com.duoduo.system.vo.RoleVO;
import com.duoduo.system.vo.UserVO;

/**
 * 权限管理Service
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:53:44
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPermissionService extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	private static Long categoryId1;
	private static Long categoryId2;

	@Resource
	private PermissionService permissionService;
	@Resource
	private PermissionCategoryService permissionCategoryService;

	// 以下用于测试listByUserId
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;

	@Test
	public void test10Create() {
		// 创建权限分类1
		PermissionCategoryVO permissionCategory = permissionCategoryService.create(TestHelper
				.createPermissionCategoryVO1());
		Assert.assertNotNull(permissionCategory);
		categoryId1 = permissionCategory.getId();
		System.out.println("categoryId1=" + categoryId1);

		// 创建权限分类2
		permissionCategory = permissionCategoryService.create(TestHelper.createPermissionCategoryVO2());
		Assert.assertNotNull(permissionCategory);
		categoryId2 = permissionCategory.getId();
		System.out.println("categoryId2=" + categoryId2);

		// 创建权限1
		PermissionVO permission = permissionService.create(TestHelper.createPermissionVO1(categoryId1, null));
		Assert.assertNotNull(permission);
		entityId1 = permission.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建权限2
		permission = permissionService.create(TestHelper.createPermissionVO2(categoryId2, null));
		Assert.assertNotNull(permission);
		entityId2 = permission.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		PermissionVO permission = permissionService.getById("" + entityId1);
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getId(), entityId1);

		permission = permissionService.getById("" + entityId2);
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getId(), entityId2);
	}

	@Test
	public void test22GetByName() {
		PermissionVO permission = permissionService.getByName("创建记录");
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getName(), "创建记录");

		permission = permissionService.getByName("删除记录");
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getName(), "删除记录");
	}

	@Test
	public void test24ListAll() {
		List<PermissionVO> permissionList = permissionService.listAll();
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 2);
	}

	@Test
	public void test26ListByCategory() {
		List<PermissionVO> permissionList = permissionService.listByCategory("" + categoryId1);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 1);
	}

	@Test
	public void test27ListByRoleId() {
		// 创建角色1
		RoleVO role = roleService.create(TestHelper.createRoleVO1(entityId1 + "," + entityId2));
		Assert.assertNotNull(role);
		Long roleId1 = role.getId();
		System.out.println("roleId1=" + roleId1);

		// 创建角色2
		role = roleService.create(TestHelper.createRoleVO2("" + entityId1));
		Assert.assertNotNull(role);
		Long roleId2 = role.getId();
		System.out.println("roleId2=" + roleId2);

		List<PermissionVO> permissionList = permissionService.listByRoleId("" + roleId1);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 2);
		System.out.println("User1's permission[" + TestHelper.getVOListNames(permissionList) + "]");

		permissionList = permissionService.listByRoleId("" + roleId2);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 1);
		System.out.println("User2's permission[" + TestHelper.getVOListNames(permissionList) + "]");

		roleService.delete("" + roleId1);
		roleService.delete("" + roleId2);
	}

	@Test
	public void test28ListByUserId() {
		// 创建角色1
		RoleVO role = roleService.create(TestHelper.createRoleVO1(entityId1 + "," + entityId2));
		Assert.assertNotNull(role);
		Long roleId1 = role.getId();
		System.out.println("roleId1=" + roleId1);

		// 创建角色2
		role = roleService.create(TestHelper.createRoleVO2("" + entityId1));
		Assert.assertNotNull(role);
		Long roleId2 = role.getId();
		System.out.println("roleId2=" + roleId2);

		// 创建用户1
		UserVO user = userService.create(TestHelper.createUserVO1(roleId1 + "," + roleId2));
		Assert.assertNotNull(user);
		Long userId1 = user.getId();
		System.out.println("userId1=" + userId1);

		// 创建用户2
		user = userService.create(TestHelper.createUserVO2("" + roleId2));
		Assert.assertNotNull(user);
		Long userId2 = user.getId();
		System.out.println("userId2=" + userId2);

		List<PermissionVO> permissionList = permissionService.listByUserId("" + userId1);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 2);
		System.out.println("User1's permission[" + TestHelper.getVOListNames(permissionList) + "]");

		permissionList = permissionService.listByUserId("" + userId2);
		Assert.assertNotNull(permissionList);
		Assert.assertEquals(permissionList.size(), 1);
		System.out.println("User2's permission[" + TestHelper.getVOListNames(permissionList) + "]");

		roleService.delete("" + roleId1);
		roleService.delete("" + roleId2);
		userService.delete("" + userId1);
		userService.delete("" + userId2);
	}

	@Test
	public void test29PageList() {
		Page<PermissionVO> page = new Page<PermissionVO>();
		page = permissionService.pagingList("创建记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<PermissionVO>();
		page = permissionService.pagingList("删除记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<PermissionVO>();
		page = permissionService.pagingList("记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		page = new Page<PermissionVO>();
		page = permissionService.pagingList(null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);
	}

	@Test
	public void test30Update() {
		PermissionVO permission = permissionService.getById("" + entityId1);
		Assert.assertNotNull(permission);

		permission.setName("修改记录");
		permissionService.update(permission);

		permission = permissionService.getById("" + entityId1);
		Assert.assertNotNull(permission);
		Assert.assertEquals(permission.getName(), "修改记录");
	}

	@Test
	public void test40Delete() {
		Assert.assertTrue(permissionService.delete("" + entityId1));
		PermissionVO permissionVO = permissionService.getById("" + entityId1);
		Assert.assertNull(permissionVO);

		Assert.assertTrue(permissionService.delete("" + entityId2));
		permissionVO = permissionService.getById("" + entityId2);
		Assert.assertNull(permissionVO);

		clearData();
	}

	private void clearData() {
		permissionCategoryService.delete("" + categoryId1);
		permissionCategoryService.delete("" + categoryId2);
	}
}
