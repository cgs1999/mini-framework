package com.duoduo.system.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.Constants;
import com.duoduo.system.vo.PermissionCategoryVO;
import com.duoduo.system.vo.PermissionVO;
import com.duoduo.system.vo.ResourceVO;
import com.duoduo.system.vo.RoleVO;
import com.duoduo.system.vo.UserVO;

/**
 * 资源管理Service单元测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:53:55
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestResourceService extends BaseTest {

	private static Long rootId;
	private static Long menuId1;
	private static Long menuId2;

	private static Long entityId1;
	private static Long entityId2;

	@javax.annotation.Resource
	private ResourceService resourceService;

	// 以下用于测试listByUserId & listByRoleId
	@javax.annotation.Resource
	private UserService userService;
	@javax.annotation.Resource
	private RoleService roleService;
	@javax.annotation.Resource
	private PermissionCategoryService permissionCategoryService;
	@javax.annotation.Resource
	private PermissionService permissionService;

	@Test
	public void test10Create() {
		// 创建资源(根菜单)
		ResourceVO resource = resourceService.create(TestHelper.createMenuRootVO());
		Assert.assertNotNull(resource);
		rootId = resource.getId();
		System.out.println("rootId=" + rootId);

		// 创建资源(菜单)1
		resource = resourceService.create(TestHelper.createMenuVO1(rootId));
		Assert.assertNotNull(resource);
		menuId1 = resource.getId();
		System.out.println("menuId1=" + menuId1);

		// 创建资源(菜单)2
		resource = resourceService.create(TestHelper.createMenuVO2(rootId));
		Assert.assertNotNull(resource);
		menuId2 = resource.getId();
		System.out.println("menuId2=" + menuId2);

		// 创建资源1
		resource = resourceService.create(TestHelper.createResourceVO1(menuId1));
		Assert.assertNotNull(resource);
		entityId1 = resource.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建资源2
		resource = resourceService.create(TestHelper.createResourceVO2(menuId2));
		Assert.assertNotNull(resource);
		entityId2 = resource.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		ResourceVO resource = resourceService.getById("" + entityId1);
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getId(), entityId1);

		resource = resourceService.getById("" + entityId2);
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getId(), entityId2);
	}

	@Test
	public void test21GetByName() {
		ResourceVO resource = resourceService.getByName("创建记录");
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getName(), "创建记录");

		resource = resourceService.getByName("删除记录");
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getName(), "删除记录");
	}

	@Test
	public void test22ListByPermissionId() {
		// 创建权限分类
		PermissionCategoryVO permissionCategory = permissionCategoryService.create(TestHelper
				.createPermissionCategoryVO1());
		Assert.assertNotNull(permissionCategory);
		Long categoryId = permissionCategory.getId();
		System.out.println("categoryId=" + categoryId);

		// 创建权限1
		PermissionVO permission = permissionService.create(TestHelper.createPermissionVO1(categoryId, entityId1 + ","
				+ entityId2));
		Assert.assertNotNull(permission);
		Long permissionId1 = permission.getId();
		System.out.println("permissionId1=" + permissionId1);

		// 创建权限2
		permission = permissionService.create(TestHelper.createPermissionVO2(categoryId, "" + entityId2));
		Assert.assertNotNull(permission);
		Long permissionId2 = permission.getId();
		System.out.println("permissionId2=" + permissionId2);

		List<ResourceVO> resourceList = resourceService.listByPermissionId("" + permissionId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);
		System.out.println("Role1's resource[" + TestHelper.getVOListNames(resourceList) + "]");

		resourceList = resourceService.listByPermissionId("" + permissionId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
		System.out.println("Role2's resource[" + TestHelper.getVOListNames(resourceList) + "]");

		permissionCategoryService.delete("" + categoryId);
		permissionService.delete("" + permissionId1);
		permissionService.delete("" + permissionId2);
	}

	@Test
	public void test23ListByRoleId() {
		// 创建权限分类
		PermissionCategoryVO permissionCategory = permissionCategoryService.create(TestHelper
				.createPermissionCategoryVO1());
		Assert.assertNotNull(permissionCategory);
		Long categoryId = permissionCategory.getId();
		System.out.println("categoryId=" + categoryId);

		// 创建权限1
		PermissionVO permission = permissionService.create(TestHelper.createPermissionVO1(categoryId, entityId1 + ","
				+ entityId2));
		Assert.assertNotNull(permission);
		Long permissionId1 = permission.getId();
		System.out.println("permissionId1=" + permissionId1);

		// 创建权限2
		permission = permissionService.create(TestHelper.createPermissionVO2(categoryId, "" + entityId2));
		Assert.assertNotNull(permission);
		Long permissionId2 = permission.getId();
		System.out.println("permissionId2=" + permissionId2);

		// 创建角色1
		RoleVO role = roleService.create(TestHelper.createRoleVO1(permissionId1 + "," + permissionId2));
		Assert.assertNotNull(role);
		Long roleId1 = role.getId();
		System.out.println("roleId1=" + roleId1);

		// 创建角色2
		role = roleService.create(TestHelper.createRoleVO2("" + permissionId2));
		Assert.assertNotNull(role);
		Long roleId2 = role.getId();
		System.out.println("roleId2=" + roleId2);

		List<ResourceVO> resourceList = resourceService.listByRoleId("" + roleId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);
		System.out.println("Role1's resource[" + TestHelper.getVOListNames(resourceList) + "]");

		resourceList = resourceService.listByRoleId("" + roleId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
		System.out.println("Role2's resource[" + TestHelper.getVOListNames(resourceList) + "]");

		roleService.delete("" + roleId1);
		roleService.delete("" + roleId2);
		permissionCategoryService.delete("" + categoryId);
		permissionService.delete("" + permissionId1);
		permissionService.delete("" + permissionId2);
	}

	@Test
	public void test24ListByUserId() {
		// 创建权限分类
		PermissionCategoryVO permissionCategory = permissionCategoryService.create(TestHelper
				.createPermissionCategoryVO1());
		Assert.assertNotNull(permissionCategory);
		Long categoryId = permissionCategory.getId();
		System.out.println("categoryId=" + categoryId);

		// 创建权限1
		PermissionVO permission = permissionService.create(TestHelper.createPermissionVO1(categoryId, entityId1 + ","
				+ entityId2));
		Assert.assertNotNull(permission);
		Long permissionId1 = permission.getId();
		System.out.println("permissionId1=" + permissionId1);

		// 创建权限2
		permission = permissionService.create(TestHelper.createPermissionVO2(categoryId, "" + entityId2));
		Assert.assertNotNull(permission);
		Long permissionId2 = permission.getId();
		System.out.println("permissionId2=" + permissionId2);

		// 创建角色1
		RoleVO role = roleService.create(TestHelper.createRoleVO1(permissionId1 + "," + permissionId2));
		Assert.assertNotNull(role);
		Long roleId1 = role.getId();
		System.out.println("roleId1=" + roleId1);

		// 创建角色2
		role = roleService.create(TestHelper.createRoleVO2("" + permissionId2));
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

		List<ResourceVO> resourceList = resourceService.listByUserId("" + userId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);
		System.out.println("User1's resource[" + TestHelper.getVOListNames(resourceList) + "]");

		resourceList = resourceService.listByUserId("" + userId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
		System.out.println("User2's resource[" + TestHelper.getVOListNames(resourceList) + "]");

		userService.delete("" + userId1);
		userService.delete("" + userId2);
		roleService.delete("" + roleId1);
		roleService.delete("" + roleId2);
		permissionCategoryService.delete("" + categoryId);
		permissionService.delete("" + permissionId1);
		permissionService.delete("" + permissionId2);
	}

	@Test
	public void test25ListSubResource() {
		List<ResourceVO> resourceList = resourceService.listSubResource("" + Constants.ROOT_MENU_ID);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);

		resourceList = resourceService.listSubResource("" + rootId);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);

		resourceList = resourceService.listSubResource("" + menuId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);

		resourceList = resourceService.listSubResource("" + menuId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
	}

	@Test
	public void test26ListAllMenu() {
		List<ResourceVO> resourceList = resourceService.listAllMenu();
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 3);
	}

	@Test
	public void test27ListAllMenuSimple() {
		List<ResourceVO> resourceList = resourceService.listAllMenuSimple();
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 3);
	}

	@Test
	public void test28ListRootMenu() {
		List<ResourceVO> resourceList = resourceService.listRootMenu();
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
	}

	@Test
	public void test29PageList() {
		Page<ResourceVO> page = new Page<ResourceVO>();
		page = resourceService.pagingList("创建记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<ResourceVO>();
		page = resourceService.pagingList("删除记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<ResourceVO>();
		page = resourceService.pagingList("记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		page = new Page<ResourceVO>();
		page = resourceService.pagingList(null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 5);
	}

	@Test
	public void test30Update() {
		ResourceVO resource = resourceService.getById("" + entityId1);
		Assert.assertNotNull(resource);

		resource.setName("修改记录");
		resourceService.update(resource);

		resource = resourceService.getById("" + entityId1);
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getName(), "修改记录");
	}

	@Test
	public void test40Delete() {
		Assert.assertTrue(resourceService.delete("" + entityId1));
		ResourceVO resourceVO = resourceService.getById("" + entityId1);
		Assert.assertNull(resourceVO);

		Assert.assertTrue(resourceService.delete("" + entityId2));
		resourceVO = resourceService.getById("" + entityId2);
		Assert.assertNull(resourceVO);

		clearData();
	}

	private void clearData() {
		resourceService.delete("" + rootId);
		resourceService.delete("" + menuId1);
		resourceService.delete("" + menuId2);
	}
}
