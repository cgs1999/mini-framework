package com.duoduo.system.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.model.Permission;
import com.duoduo.system.model.PermissionCategory;
import com.duoduo.system.model.PermissionResource;
import com.duoduo.system.model.Resource;
import com.duoduo.system.model.Role;
import com.duoduo.system.model.RolePermission;
import com.duoduo.system.model.User;
import com.duoduo.system.model.UserRole;

/**
 * 资源管理Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:35:34
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestResourceDao extends BaseTest {

	private static Long rootId;
	private static Long menuId1;
	private static Long menuId2;

	private static Long entityId1;
	private static Long entityId2;

	@javax.annotation.Resource
	private ResourceDao resourceDao;

	// 以下用于测试listByUserId & listByRoleId
	@javax.annotation.Resource
	private UserDao userDao;
	@javax.annotation.Resource
	private RoleDao roleDao;
	@javax.annotation.Resource
	private UserRoleDao userRoleDao;
	@javax.annotation.Resource
	private RolePermissionDao rolePermissionDao;
	@javax.annotation.Resource
	private PermissionCategoryDao permissionCategoryDao;
	@javax.annotation.Resource
	private PermissionDao permissionDao;
	@javax.annotation.Resource
	private PermissionResourceDao permissionResourceDao;

	@Test
	public void test10Create() {
		// 创建资源(根菜单)
		Resource resource = resourceDao.create(TestHelper.createMenuRoot());
		Assert.assertNotNull(resource);
		rootId = resource.getId();
		System.out.println("rootId=" + rootId);

		// 创建资源(菜单)1
		resource = resourceDao.create(TestHelper.createMenu1(rootId));
		Assert.assertNotNull(resource);
		menuId1 = resource.getId();
		System.out.println("menuId1=" + menuId1);

		// 创建资源(菜单)2
		resource = resourceDao.create(TestHelper.createMenu2(rootId));
		Assert.assertNotNull(resource);
		menuId2 = resource.getId();
		System.out.println("menuId2=" + menuId2);

		// 创建资源1
		resource = resourceDao.create(TestHelper.createResource1(menuId1));
		Assert.assertNotNull(resource);
		entityId1 = resource.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建资源2
		resource = resourceDao.create(TestHelper.createResource2(menuId2));
		Assert.assertNotNull(resource);
		entityId2 = resource.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		Resource resource = resourceDao.getById("" + entityId1);
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getId(), entityId1);

		resource = resourceDao.getById("" + entityId2);
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getId(), entityId2);
	}

	@Test
	public void test21GetByName() {
		Resource resource = resourceDao.getByName("创建记录");
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getName(), "创建记录");

		resource = resourceDao.getByName("删除记录");
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getName(), "删除记录");
	}

	@Test
	public void test22ListByType() {
		List<Resource> resourceList = resourceDao.listByType("1");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 3);

		resourceList = resourceDao.listByType("2");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);
	}

	@Test
	public void test23ListByPermissionId() {
		// 创建权限分类
		PermissionCategory permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory1());
		Assert.assertNotNull(permissionCategory);
		Long categoryId = permissionCategory.getId();
		System.out.println("categoryId=" + categoryId);

		// 创建权限1
		Permission permission = permissionDao.create(TestHelper.createPermission1(categoryId));
		Assert.assertNotNull(permission);
		Long permissionId1 = permission.getId();
		System.out.println("permissionId1=" + permissionId1);

		// 创建权限2
		permission = permissionDao.create(TestHelper.createPermission2(categoryId));
		Assert.assertNotNull(permission);
		Long permissionId2 = permission.getId();
		System.out.println("permissionId2=" + permissionId2);

		// 创建权限资源关系1
		PermissionResource permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(entityId1);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建权限资源关系2
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(entityId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建权限资源关系3
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId2);
		permissionResource.setResourceId(entityId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		List<Resource> resourceList = resourceDao.listByPermissionId("" + permissionId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);
		System.out.println("Role1's resource[" + TestHelper.getEntityListNames(resourceList) + "]");

		resourceList = resourceDao.listByPermissionId("" + permissionId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
		System.out.println("Role2's resource[" + TestHelper.getEntityListNames(resourceList) + "]");

		permissionCategoryDao.delete("" + categoryId);
		permissionDao.delete("" + permissionId1);
		permissionDao.delete("" + permissionId2);
		permissionResourceDao.deleteByPermissionId("" + permissionId1);
		permissionResourceDao.deleteByPermissionId("" + permissionId2);
		permissionResourceDao.deleteByResourceId("" + entityId1);
		permissionResourceDao.deleteByResourceId("" + entityId2);
	}

	@Test
	public void test24ListByRoleId() {
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

		// 创建权限分类
		PermissionCategory permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory1());
		Assert.assertNotNull(permissionCategory);
		Long categoryId = permissionCategory.getId();
		System.out.println("categoryId=" + categoryId);

		// 创建权限1
		Permission permission = permissionDao.create(TestHelper.createPermission1(categoryId));
		Assert.assertNotNull(permission);
		Long permissionId1 = permission.getId();
		System.out.println("permissionId1=" + permissionId1);

		// 创建权限2
		permission = permissionDao.create(TestHelper.createPermission2(categoryId));
		Assert.assertNotNull(permission);
		Long permissionId2 = permission.getId();
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

		// 创建权限资源关系1
		PermissionResource permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(entityId1);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建权限资源关系2
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(entityId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建权限资源关系3
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId2);
		permissionResource.setResourceId(entityId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		List<Resource> resourceList = resourceDao.listByRoleId("" + roleId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);
		System.out.println("Role1's resource[" + TestHelper.getEntityListNames(resourceList) + "]");

		resourceList = resourceDao.listByRoleId("" + roleId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
		System.out.println("Role2's resource[" + TestHelper.getEntityListNames(resourceList) + "]");

		roleDao.delete("" + roleId1);
		roleDao.delete("" + roleId2);
		rolePermissionDao.deleteByRoleId("" + roleId1);
		rolePermissionDao.deleteByRoleId("" + roleId2);
		rolePermissionDao.deleteByPermissionId("" + permissionId1);
		rolePermissionDao.deleteByPermissionId("" + permissionId2);
		permissionCategoryDao.delete("" + categoryId);
		permissionDao.delete("" + permissionId1);
		permissionDao.delete("" + permissionId2);
		permissionResourceDao.deleteByPermissionId("" + permissionId1);
		permissionResourceDao.deleteByPermissionId("" + permissionId2);
		permissionResourceDao.deleteByResourceId("" + entityId1);
		permissionResourceDao.deleteByResourceId("" + entityId2);
	}

	@Test
	public void test25ListByUserId() {
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

		// 创建权限分类
		PermissionCategory permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory1());
		Assert.assertNotNull(permissionCategory);
		Long categoryId = permissionCategory.getId();
		System.out.println("categoryId=" + categoryId);

		// 创建权限1
		Permission permission = permissionDao.create(TestHelper.createPermission1(categoryId));
		Assert.assertNotNull(permission);
		Long permissionId1 = permission.getId();
		System.out.println("permissionId1=" + permissionId1);

		// 创建权限2
		permission = permissionDao.create(TestHelper.createPermission2(categoryId));
		Assert.assertNotNull(permission);
		Long permissionId2 = permission.getId();
		System.out.println("permissionId2=" + permissionId2);

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

		// 创建权限资源关系1
		PermissionResource permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(entityId1);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建权限资源关系2
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(entityId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建权限资源关系3
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId2);
		permissionResource.setResourceId(entityId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		List<Resource> resourceList = resourceDao.listByUserId("" + userId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);
		System.out.println("User1's resource[" + TestHelper.getEntityListNames(resourceList) + "]");

		resourceList = resourceDao.listByUserId("" + userId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
		System.out.println("User2's resource[" + TestHelper.getEntityListNames(resourceList) + "]");

		userDao.delete("" + userId1);
		userDao.delete("" + userId2);
		userRoleDao.deleteByUserId("" + userId1);
		userRoleDao.deleteByUserId("" + userId2);
		userRoleDao.deleteByRoleId("" + roleId1);
		userRoleDao.deleteByRoleId("" + roleId2);
		roleDao.delete("" + roleId1);
		roleDao.delete("" + roleId2);
		rolePermissionDao.deleteByRoleId("" + roleId1);
		rolePermissionDao.deleteByRoleId("" + roleId2);
		rolePermissionDao.deleteByPermissionId("" + permissionId1);
		rolePermissionDao.deleteByPermissionId("" + permissionId2);
		permissionCategoryDao.delete("" + categoryId);
		permissionDao.delete("" + permissionId1);
		permissionDao.delete("" + permissionId2);
		permissionResourceDao.deleteByPermissionId("" + permissionId1);
		permissionResourceDao.deleteByPermissionId("" + permissionId2);
		permissionResourceDao.deleteByResourceId("" + entityId1);
		permissionResourceDao.deleteByResourceId("" + entityId2);
	}

	@Test
	public void test26ListSubResource() {
		List<Resource> resourceList = resourceDao.listSubResource(null);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);

		resourceList = resourceDao.listSubResource("" + rootId);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);

		resourceList = resourceDao.listSubResource("" + menuId1);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);

		resourceList = resourceDao.listSubResource("" + menuId2);
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
	}

	@Test
	public void test27ListSubResourceByType() {
		List<Resource> resourceList = resourceDao.listSubResourceByType(null, "1");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);

		resourceList = resourceDao.listSubResourceByType("" + rootId, "1");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 2);

		resourceList = resourceDao.listSubResourceByType("" + rootId, "2");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 0);

		resourceList = resourceDao.listSubResourceByType("" + menuId1, "1");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 0);

		resourceList = resourceDao.listSubResourceByType("" + menuId1, "2");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);

		resourceList = resourceDao.listSubResourceByType("" + menuId2, "1");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 0);

		resourceList = resourceDao.listSubResourceByType("" + menuId2, "2");
		Assert.assertNotNull(resourceList);
		Assert.assertEquals(resourceList.size(), 1);
	}

	@Test
	public void test28PageList() {
		Page<Resource> page = new Page<Resource>();
		page = resourceDao.pagingList("创建记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<Resource>();
		page = resourceDao.pagingList("删除记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<Resource>();
		page = resourceDao.pagingList("记录", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		page = new Page<Resource>();
		page = resourceDao.pagingList(null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 5);
	}

	@Test
	public void test30Update() {
		Resource resource = resourceDao.getById("" + entityId1);
		Assert.assertNotNull(resource);

		resource.setName("修改记录");
		resourceDao.update(resource);

		resource = resourceDao.getById("" + entityId1);
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getName(), "修改记录");
	}

	@Test
	public void test40Delete() {
		Assert.assertTrue(resourceDao.delete("" + entityId1));
		Resource resource = resourceDao.getById("" + entityId1);
		Assert.assertNull(resource);

		Assert.assertTrue(resourceDao.delete("" + entityId2));
		resource = resourceDao.getById("" + entityId2);
		Assert.assertNull(resource);

		Page<Resource> page = new Page<Resource>();
		page = resourceDao.pagingList(null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 3);

		clearData();
	}

	private void clearData() {
		resourceDao.delete("" + rootId);
		resourceDao.delete("" + menuId1);
		resourceDao.delete("" + menuId2);
	}
}
