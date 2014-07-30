package com.duoduo.system.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.system.model.Permission;
import com.duoduo.system.model.PermissionCategory;
import com.duoduo.system.model.PermissionResource;
import com.duoduo.system.model.Resource;

/**
 * 权限资源关系Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:36:11
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPermissionResourceDao extends BaseTest {

	private static Long categoryId;

	private static Long permissionId1;
	private static Long permissionId2;

	private static Long resourceId1;
	private static Long resourceId2;

	@javax.annotation.Resource
	private PermissionResourceDao permissionResourceDao;
	@javax.annotation.Resource
	private PermissionCategoryDao permissionCategoryDao;
	@javax.annotation.Resource
	private PermissionDao permissionDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;

	@Test
	public void test10Create() {
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

		// 创建资源1
		Resource resource = resourceDao.create(TestHelper.createResource1(null));
		Assert.assertNotNull(resource);
		resourceId1 = resource.getId();
		System.out.println("resourceId1=" + resourceId1);

		// 创建资源2
		resource = resourceDao.create(TestHelper.createResource2(null));
		Assert.assertNotNull(resource);
		resourceId2 = resource.getId();
		System.out.println("resourceId2=" + resourceId2);

		// 创建用户角色关系1
		PermissionResource permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(resourceId1);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建用户角色关系2
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId1);
		permissionResource.setResourceId(resourceId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));

		// 创建用户角色关系3
		permissionResource = new PermissionResource();
		permissionResource.setPermissionId(permissionId2);
		permissionResource.setResourceId(resourceId2);
		Assert.assertTrue(permissionResourceDao.create(permissionResource));
	}

	@Test
	public void test20ListByPermissionId() {
		// permission1
		List<PermissionResource> permissionResourceList = permissionResourceDao.listByPermissionId("" + permissionId1);
		Assert.assertNotNull(permissionResourceList);
		Assert.assertEquals(permissionResourceList.size(), 2);

		// permission2
		permissionResourceList = permissionResourceDao.listByPermissionId("" + permissionId2);
		Assert.assertNotNull(permissionResourceList);
		Assert.assertEquals(permissionResourceList.size(), 1);
	}

	@Test
	public void test22ListByResourceId() {
		// resource1
		List<PermissionResource> permissionResourceList = permissionResourceDao.listByResourceId("" + resourceId1);
		Assert.assertNotNull(permissionResourceList);
		Assert.assertEquals(permissionResourceList.size(), 1);

		// resource2
		permissionResourceList = permissionResourceDao.listByResourceId("" + resourceId2);
		Assert.assertNotNull(permissionResourceList);
		Assert.assertEquals(permissionResourceList.size(), 2);
	}

	@Test
	public void test30DeleteByPermissionId() {
		Assert.assertTrue(permissionResourceDao.deleteByPermissionId("" + permissionId1));

		// permission1
		List<PermissionResource> permissionResourceList = permissionResourceDao.listByPermissionId("" + permissionId1);
		Assert.assertNotNull(permissionResourceList);
		Assert.assertEquals(permissionResourceList.size(), 0);
	}

	@Test
	public void test32DeleteByResourceId() {
		Assert.assertTrue(permissionResourceDao.deleteByResourceId("" + resourceId2));

		// permission1
		List<PermissionResource> permissionResourceList = permissionResourceDao.listByResourceId("" + resourceId2);
		Assert.assertNotNull(permissionResourceList);
		Assert.assertEquals(permissionResourceList.size(), 0);

		clearData();
	}

	private void clearData() {
		permissionCategoryDao.delete("" + categoryId);
		permissionDao.delete("" + permissionId1);
		permissionDao.delete("" + permissionId2);
		resourceDao.delete("" + resourceId1);
		resourceDao.delete("" + resourceId2);
	}
}
