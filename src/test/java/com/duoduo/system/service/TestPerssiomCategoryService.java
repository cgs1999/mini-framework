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

/**
 * 权限分类管理Service单元测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:53:35
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPerssiomCategoryService extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	@Resource
	private PermissionCategoryService permissionCategoryService;

	@Test
	public void test10Create() {
		// 创建权限分类1
		PermissionCategoryVO permissionCategory = permissionCategoryService.create(TestHelper
				.createPermissionCategoryVO1());

		Assert.assertNotNull(permissionCategory);
		entityId1 = permissionCategory.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建权限分类2
		permissionCategory = permissionCategoryService.create(TestHelper.createPermissionCategoryVO2());

		Assert.assertNotNull(permissionCategory);
		entityId2 = permissionCategory.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		PermissionCategoryVO permissionCategory = permissionCategoryService.getById("" + entityId1);
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getId(), entityId1);

		permissionCategory = permissionCategoryService.getById("" + entityId2);
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getId(), entityId2);
	}

	@Test
	public void test22GetByName() {
		PermissionCategoryVO permissionCategory = permissionCategoryService.getByName("用户管理");
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getName(), "用户管理");

		permissionCategory = permissionCategoryService.getByName("角色管理");
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getName(), "角色管理");
	}

	@Test
	public void test24ListAll() {
		List<PermissionCategoryVO> permissionCategoryList = permissionCategoryService.listAll();
		Assert.assertNotNull(permissionCategoryList);
		Assert.assertEquals(permissionCategoryList.size(), 2);
	}

	@Test
	public void test26PageList() {
		Page<PermissionCategoryVO> page = new Page<PermissionCategoryVO>();
		page = permissionCategoryService.pagingList("用户管理", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<PermissionCategoryVO>();
		page = permissionCategoryService.pagingList("角色管理", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<PermissionCategoryVO>();
		page = permissionCategoryService.pagingList("管理", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		page = new Page<PermissionCategoryVO>();
		page = permissionCategoryService.pagingList(null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);
	}

	@Test
	public void test30Update() {
		PermissionCategoryVO permissionCategory = permissionCategoryService.getById("" + entityId1);
		Assert.assertNotNull(permissionCategory);

		permissionCategory.setName("菜单管理");
		permissionCategoryService.update(permissionCategory);

		permissionCategory = permissionCategoryService.getById("" + entityId1);
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getName(), "菜单管理");
	}

	@Test
	public void test40Delete() {
		Assert.assertTrue(permissionCategoryService.delete("" + entityId1));
		PermissionCategoryVO permissionCategoryVO = permissionCategoryService.getById("" + entityId1);
		Assert.assertNull(permissionCategoryVO);

		Assert.assertTrue(permissionCategoryService.delete("" + entityId2));
		permissionCategoryVO = permissionCategoryService.getById("" + entityId2);
		Assert.assertNull(permissionCategoryVO);
	}
}
