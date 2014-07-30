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
import com.duoduo.system.model.PermissionCategory;

/**
 * 权限分类Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:35:00
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPermissionCategoryDao extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	@Resource
	private PermissionCategoryDao permissionCategoryDao;

	@Test
	public void test10Create() {
		// 创建权限分类1
		PermissionCategory permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory1());

		Assert.assertNotNull(permissionCategory);
		entityId1 = permissionCategory.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建权限分类2
		permissionCategory = permissionCategoryDao.create(TestHelper.createPermissionCategory2());

		Assert.assertNotNull(permissionCategory);
		entityId2 = permissionCategory.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		PermissionCategory permissionCategory = permissionCategoryDao.getById("" + entityId1);
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getId(), entityId1);

		permissionCategory = permissionCategoryDao.getById("" + entityId2);
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getId(), entityId2);
	}

	@Test
	public void test22GetByName() {
		PermissionCategory permissionCategory = permissionCategoryDao.getByName("用户管理");
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getName(), "用户管理");

		permissionCategory = permissionCategoryDao.getByName("角色管理");
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getName(), "角色管理");
	}

	@Test
	public void test24ListAll() {
		List<PermissionCategory> permissionCategoryList = permissionCategoryDao.listAll();
		Assert.assertNotNull(permissionCategoryList);
		Assert.assertEquals(permissionCategoryList.size(), 2);
	}

	@Test
	public void test26PageList() {
		Page<PermissionCategory> page = new Page<PermissionCategory>();
		page = permissionCategoryDao.pagingList("用户管理", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<PermissionCategory>();
		page = permissionCategoryDao.pagingList("角色管理", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		page = new Page<PermissionCategory>();
		page = permissionCategoryDao.pagingList("管理", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		page = new Page<PermissionCategory>();
		page = permissionCategoryDao.pagingList(null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);
	}

	@Test
	public void test30Update() {
		PermissionCategory permissionCategory = permissionCategoryDao.getById("" + entityId1);
		Assert.assertNotNull(permissionCategory);

		permissionCategory.setName("菜单管理");
		permissionCategoryDao.update(permissionCategory);

		permissionCategory = permissionCategoryDao.getById("" + entityId1);
		Assert.assertNotNull(permissionCategory);
		Assert.assertEquals(permissionCategory.getName(), "菜单管理");
	}

	@Test
	public void test40Delete() {
		permissionCategoryDao.delete("" + entityId1);
		permissionCategoryDao.delete("" + entityId2);

		List<PermissionCategory> permissionCategoryList = permissionCategoryDao.listAll();
		Assert.assertNotNull(permissionCategoryList);
		Assert.assertEquals(permissionCategoryList.size(), 0);
	}
}
